package tienda.alyso.dao;

import conexion.ConexionOracle;
import tienda.alyso.modelo.Pedido;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import oracle.jdbc.OracleTypes;


public class PedidoDAO {

    /**
     * Inserta un nuevo pedido y devuelve su ID.
     */
     public List<Pedido> listarPorCliente(int idCliente) {
        List<Pedido> lista = new ArrayList<>();
        String call = "{ call SP_CONSULTAR_PEDIDOS_POR_CLIENTE(?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, idCliente);
            cs.registerOutParameter(2, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                while (rs.next()) {
                    int    id     = rs.getInt("ID_PEDIDO");
                    Date   fecha  = rs.getDate("FECHA");
                    double total  = rs.getDouble("TOTAL");
                    lista.add(new Pedido(id, idCliente, fecha.toString(), total));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public int insertarPedido(int idCliente, double total) {
        String call = "{ call SP_INSERTAR_PEDIDO(?, ?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, idCliente);
            cs.setDouble(2, total);
            cs.registerOutParameter(3, Types.NUMERIC);

            cs.execute();
            return cs.getInt(3);

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Inserta la relación pedido–producto con cantidad, precio y si está personalizado.
     */
    public boolean insertarPedidoProducto(int idPedido,
                                          int idProducto,
                                          int cantidad,
                                          double precioUnitario,
                                          boolean personalizado) {
        String call = "{ call SP_INSERTAR_PEDIDO_PRODUCTO(?, ?, ?, ?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, idPedido);
            cs.setInt(2, idProducto);
            cs.setInt(3, cantidad);
            cs.setDouble(4, precioUnitario);
            cs.setString(5, personalizado ? "S" : "N");

            cs.execute();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Inserta un diseño personalizado y devuelve su ID.
     */
    public int insertarDisenoPersonalizado(int idPedido,
                                       String descripcion,
                                       byte[] imagen) {
    String call = "{ call SP_INSERTAR_DISENO_PERSONALIZADO(?, ?, ?, ?) }";
    try (Connection conn = ConexionOracle.conectar();
         CallableStatement cs = conn.prepareCall(call)) {

        cs.setInt(1, idPedido);
        cs.setString(2, descripcion);
        // Pasa el arreglo de bytes al parámetro BLOB
        cs.setBytes(3, imagen);
        cs.registerOutParameter(4, Types.NUMERIC);

        cs.execute();
        return cs.getInt(4);

    } catch (SQLException ex) {
        ex.printStackTrace();
        return -1;
    }
    }
    
    public boolean actualizarEstadoPedido(int idPedido, String estado) {
        String call = "{ call SP_ACTUALIZAR_ESTADO_PEDIDO(?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {
            cs.setInt(1, idPedido);
            cs.setString(2, estado);
            cs.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean eliminarPedido(int idPedido) {
            String call = "{ call SP_ELIMINAR_PEDIDO(?) }";
            try (Connection conn = ConexionOracle.conectar();
                 CallableStatement cs = conn.prepareCall(call)) {
                cs.setInt(1, idPedido);
                cs.execute();
                return true;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
    }
    public String generarEtiquetaEnvio(int idPedido) {
        StringBuilder sb = new StringBuilder();
        // 1) Obtener datos de pedido + cliente + estado
        String callPedido = "{ call SP_CONSULTAR_PEDIDO_POR_ID(?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs1 = conn.prepareCall(callPedido)) {

            cs1.setInt(1, idPedido);
            cs1.registerOutParameter(2, OracleTypes.CURSOR);
            cs1.execute();
            try (ResultSet rs = (ResultSet) cs1.getObject(2)) {
                if (rs.next()) {
                    sb.append("Etiqueta de Envío\n")
                      .append("-------------------------------\n")
                      .append("Pedido ID: ").append(rs.getInt("ID_PEDIDO")).append("\n")
                      .append("Fecha: ").append(rs.getDate("FECHA")).append("\n")
                      .append("Total: ").append(rs.getDouble("TOTAL")).append("₡\n")
                      .append("Estado: ").append(rs.getString("ESTADO")).append("\n\n")
                      .append("Cliente:\n")
                      .append("  ID: ").append(rs.getInt("ID_CLIENTE")).append("\n")
                      .append("  Nombre: ").append(rs.getString("CLIENTE_NOMBRE"))
                      .append(" ").append(rs.getString("CLIENTE_APELLIDO")).append("\n")
                      .append("  Teléfono: ").append(rs.getString("CLIENTE_TELEFONO")).append("\n")
                      .append("  Correo: ").append(rs.getString("CLIENTE_CORREO")).append("\n\n")
                      .append("Productos:\n");
                }
            }

            // 2) Obtener detalle de productos
            String callProd = "{ call SP_CONSULTAR_PRODUCTOS_POR_PEDIDO(?, ?) }";
            try (CallableStatement cs2 = conn.prepareCall(callProd)) {
                cs2.setInt(1, idPedido);
                cs2.registerOutParameter(2, OracleTypes.CURSOR);
                cs2.execute();
                try (ResultSet rs2 = (ResultSet) cs2.getObject(2)) {
                    while (rs2.next()) {
                        sb.append("  - ").append(rs2.getString("NOMBRE"))
                          .append(" x").append(rs2.getInt("CANTIDAD"))
                          .append(" @").append(rs2.getDouble("PRECIO_UNITARIO")).append("₡")
                          .append(" (Pers.: ").append(rs2.getString("PERSONALIZADO")).append(")\n");
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error generando etiqueta";
        }
        return sb.toString();
    }
    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String call = "{ call SP_CONSULTAR_PEDIDOS(?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    int    id       = rs.getInt("ID_PEDIDO");
                    int    idCli    = rs.getInt("ID_CLIENTE");
                    String fecha    = rs.getString("FECHA");      // ya formateada YYYY-MM-DD
                    double total    = rs.getDouble("TOTAL");
                    String estado   = rs.getString("ESTADO");

                    // Usa el constructor que incluye estado
                    lista.add(new Pedido(id, idCli, fecha, total, estado));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

// ReportesDAO.java
package tienda.alyso.dao;

import conexion.ConexionOracle;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportesDAO {

    /** Productos vendidos en el último mes, opcionalmente filtrado por correo */
    public List<Object[]> productosVendidosUltimoMes(String email) {
        List<Object[]> lista = new ArrayList<>();
        String call = "{ call SP_REP_PRODUCTOS_VENDIDOS_ULTIMO_MES(?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            if (email == null || email.isEmpty()) {
                cs.setNull(1, Types.VARCHAR);
            } else {
                cs.setString(1, email);
            }
            cs.registerOutParameter(2, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("ID_PRODUCTO"),
                        rs.getString("NOMBRE"),
                        rs.getInt("TOTAL_VENDIDO")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /** Clientes con más pedidos */
    public List<Object[]> topClientesPorPedidos() {
        List<Object[]> lista = new ArrayList<>();
        String call = "{ call SP_REP_TOP_CLIENTES_POR_PEDIDOS(?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("ID_CLIENTE"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getInt("PEDIDOS_COUNT")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /** Clientes con mayor valor total de pedidos */
    public List<Object[]> topClientesPorValor() {
        List<Object[]> lista = new ArrayList<>();
        String call = "{ call SP_REP_TOP_CLIENTES_POR_VALOR(?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("ID_CLIENTE"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getDouble("TOTAL_VENTAS")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /** Historial de ventas por cliente, filtrado por correo */
    public List<Object[]> ventasPorCliente(String email) {
        List<Object[]> lista = new ArrayList<>();
        String call = "{ call SP_REP_VENTAS_POR_CLIENTE(?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, email);
            cs.registerOutParameter(2, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("ID_PEDIDO"),
                        rs.getString("FECHA"),
                        rs.getDouble("TOTAL"),
                        rs.getString("ESTADO")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

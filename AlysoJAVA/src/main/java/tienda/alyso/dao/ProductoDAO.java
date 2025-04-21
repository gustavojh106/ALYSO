package tienda.alyso.dao;

import tienda.alyso.modelo.Producto;
import conexion.ConexionOracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ProductoDAO {

    /** Lista todos los productos */
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String call = "{ call SP_CONSULTAR_PRODUCTOS(?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            try (ResultSet rs = (ResultSet)cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(new Producto(
                        rs.getInt("ID_PRODUCTO"),
                        rs.getString("NOMBRE"),
                        rs.getDouble("PRECIO"),
                        rs.getString("DESCRIPCION"),
                        rs.getInt("STOCK")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public boolean actualizarStock(int idProducto, int delta) {
            String call = "{ call SP_ACTUALIZAR_STOCK_PRODUCTO(?, ?) }";
            try (Connection conn = ConexionOracle.conectar();
                 CallableStatement cs = conn.prepareCall(call)) {

                cs.setInt(1, idProducto);
                cs.setInt(2, delta);
                cs.execute();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    /** Inserta un producto y devuelve su ID generado */
    public int insertarProducto(Producto p) {
        String call = "{ call SP_INSERTAR_PRODUCTO(?, ?, ?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, p.getNombre());
            cs.setDouble(2, p.getPrecio());
            cs.setString(3, p.getDescripcion());
            cs.registerOutParameter(4, Types.NUMERIC);
            cs.execute();
            return cs.getInt(4);

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /** Actualiza un producto existente */
    public boolean actualizarProducto(Producto p) {
        String call = "{ call SP_ACTUALIZAR_PRODUCTO(?, ?, ?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, p.getIdProducto());
            cs.setString(2, p.getNombre());
            cs.setDouble(3, p.getPrecio());
            cs.setString(4, p.getDescripcion());
            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Elimina un producto por su ID */
    public boolean eliminarProducto(int idProducto) {
        String call = "{ call SP_ELIMINAR_PRODUCTO(?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, idProducto);
            cs.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

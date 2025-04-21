// EnvioDAO.java
package tienda.alyso.dao;

import tienda.alyso.modelo.Envio;
import conexion.ConexionOracle;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnvioDAO {

    /** Lista todos los envíos */
    public List<Envio> listarEnvíos() {
        List<Envio> lista = new ArrayList<>();
        String call = "{ call SP_CONSULTAR_ENVIOS(?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            try (ResultSet rs = (ResultSet)cs.getObject(1)) {
                while (rs.next()) {
                    lista.add(new Envio(
                        rs.getInt("ID_ENVIO"),
                        rs.getInt("ID_PEDIDO"),
                        rs.getString("FECHA_ENVIO"),
                        rs.getString("EMPRESA"),
                        rs.getString("ESTADO_ENVIO"),
                        rs.getString("DETALLE")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /** Actualiza estado y detalle de seguimiento */
    public boolean actualizarEnvio(int idEnvio, String estado, String detalle) {
        String call = "{ call SP_ACTUALIZAR_ENVIO(?, ?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, idEnvio);
            cs.setString(2, estado);
            cs.setString(3, detalle);
            cs.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Elimina un envío */
    public boolean eliminarEnvio(int idEnvio) {
        String call = "{ call SP_ELIMINAR_ENVIO(?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, idEnvio);
            cs.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

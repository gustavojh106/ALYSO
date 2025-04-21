// ClienteDAO.java
package tienda.alyso.dao;

import tienda.alyso.modelo.Cliente;
import conexion.ConexionOracle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ClienteDAO {

    /** Lista todos los clientes */
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String call = "{ call SP_CONSULTAR_CLIENTES(?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    Cliente c = new Cliente();
                    c.setIdCliente(rs.getInt("ID_CLIENTE"));
                    c.setNombre(rs.getString("NOMBRE"));
                    c.setApellido(rs.getString("APELLIDO"));
                    c.setTelefono(rs.getString("TELEFONO"));
                    c.setCorreo(rs.getString("CORREO"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /** Inserta un cliente */
    public boolean insertar(Cliente cliente) {
        String call = "{ call SP_INSERTAR_CLIENTE(?, ?, ?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setString(1, cliente.getNombre());
            cs.setString(2, cliente.getApellido());
            cs.setString(3, cliente.getTelefono());
            cs.setString(4, cliente.getCorreo());
            cs.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Actualiza un cliente */
    public boolean actualizar(Cliente cliente) {
        String call = "{ call SP_ACTUALIZAR_CLIENTE(?, ?, ?, ?, ?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, cliente.getIdCliente());
            cs.setString(2, cliente.getNombre());
            cs.setString(3, cliente.getApellido());
            cs.setString(4, cliente.getTelefono());
            cs.setString(5, cliente.getCorreo());
            cs.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Elimina un cliente */
    public boolean eliminar(int idCliente) {
        String call = "{ call SP_ELIMINAR_CLIENTE(?) }";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, idCliente);
            cs.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     public Cliente buscarPorEmail(String email) {
        String sql = "{ call SP_CONSULTAR_CLIENTE_POR_EMAIL(?, ?) }";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, email);
            cs.registerOutParameter(2, OracleTypes.CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                if (rs.next()) {
                    // Usa el constructor vacío y setters según tu modelo
                    Cliente c = new Cliente();
                    c.setIdCliente( rs.getInt("ID_CLIENTE") );
                    c.setNombre(    rs.getString("NOMBRE")    );
                    c.setApellido(  rs.getString("APELLIDO")  );
                    c.setTelefono(  rs.getString("TELEFONO")  );
                    c.setCorreo(    rs.getString("CORREO")    );
                    return c;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por email: " + e.getMessage());
        }

        return null;
    }
    
    
}

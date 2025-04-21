package tienda.alyso.dao;

import tienda.alyso.modelo.Cliente;
import conexion.ConexionOracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Insertar un nuevo cliente
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO CLIENTE (ID_CLIENTE, NOMBRE, APELLIDO, TELEFONO, CORREO) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellido());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getCorreo());

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    // Obtener todos los clientes
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTE";

        try (Connection conn = ConexionOracle.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("ID_CLIENTE"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getString("TELEFONO"),
                    rs.getString("CORREO")
                );
                lista.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }

    // Buscar cliente por ID
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM CLIENTE WHERE ID_CLIENTE = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                    rs.getInt("ID_CLIENTE"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getString("TELEFONO"),
                    rs.getString("CORREO")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    // Actualizar cliente
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE CLIENTE SET NOMBRE = ?, APELLIDO = ?, TELEFONO = ?, CORREO = ? WHERE ID_CLIENTE = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getCorreo());
            stmt.setInt(5, cliente.getIdCliente());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Eliminar cliente
    public boolean eliminar(int id) {
        String sql = "DELETE FROM CLIENTE WHERE ID_CLIENTE = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}

package tienda.alyso.dao;

import conexion.ConexionOracle;
import tienda.alyso.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductoDAO {

    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO PRODUCTO (ID_PRODUCTO, NOMBRE, PRECIO, DESCRIPCION) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, producto.getIdProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setString(4, producto.getDescripcion());

            int filas = stmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar producto: " + e.getMessage());
            return false;
        }
    }
}

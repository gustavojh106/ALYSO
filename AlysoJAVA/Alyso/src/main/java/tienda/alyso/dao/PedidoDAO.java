package tienda.alyso.dao;

import tienda.alyso.modelo.Pedido;
import conexion.ConexionOracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PedidoDAO {

    public boolean insertar(Pedido pedido) {
        String sql = "INSERT INTO PEDIDO (ID_PEDIDO, ID_CLIENTE, FECHA, TOTAL) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?)";

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdPedido());
            stmt.setInt(2, pedido.getIdCliente());
            stmt.setString(3, pedido.getFecha());
            stmt.setDouble(4, pedido.getTotal());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar pedido: " + e.getMessage());
            return false;
        }
    }
}

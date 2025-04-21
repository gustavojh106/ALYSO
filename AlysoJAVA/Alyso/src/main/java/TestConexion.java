import java.sql.Connection;
import conexion.ConexionOracle;

public class TestConexion {
    public static void main(String[] args) {
        Connection con = ConexionOracle.conectar();
        if (con != null) {
             System.out.println("✅ Conexión exitosa a Oracle");
        } else {
            System.out.println("❌ Falló la conexión.");
        }
    }
}

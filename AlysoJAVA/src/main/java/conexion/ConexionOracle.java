package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    
    //como formar el string de conexion: ir a SQL developer, click derecho a la conexion y en propiedades
    //la parte jdbc:oracle:thin:@ es fija, no se cambia nunca, solo se cambian los parametros segun la conexion
    //Hostname: localhost
    //Port: 1521
    //sid: alyso
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:alyso";
    private static final String USER = "SYSTEM";
    private static final String PASS = "alyso";

    public static Connection conectar() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }
}
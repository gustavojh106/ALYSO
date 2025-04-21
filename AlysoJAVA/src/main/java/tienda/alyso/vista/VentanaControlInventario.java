// VentanaControlInventario.java (stub)
package tienda.alyso.vista;

import javax.swing.*;
import java.awt.*;

public class VentanaControlInventario extends JFrame {
    public VentanaControlInventario() {
        setTitle("Control de Inventario");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Aquí irá el control de inventario
        JLabel lbl = new JLabel("Aquí irán las funciones de control de inventario", SwingConstants.CENTER);
        add(lbl, BorderLayout.CENTER);
    }
}

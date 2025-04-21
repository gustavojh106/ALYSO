// VentanaAdministrador.java
package tienda.alyso.vista;

import javax.swing.*;
import java.awt.*;

public class VentanaAdministrador extends JFrame {

    public VentanaAdministrador() {
        setTitle("Administrador - Menú Principal");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnGestionProductos     = new JButton("Gestión de Productos");
        JButton btnGestionClientes      = new JButton("Gestión de Clientes");
        JButton btnGestionPedidos       = new JButton("Gestión de Pedidos");
//        JButton btnControlInventario    = new JButton("Control de Inventario");
        JButton btnReportesVentas       = new JButton("Generación de Reportes de Ventas");
        JButton btnGestionEnvios        = new JButton("Gestión de Envíos");
        JButton btnVolver               = new JButton("Volver");

        btnGestionProductos.addActionListener(e -> {
            new VentanaGestionProductos().setVisible(true);
            dispose();
        });
        btnGestionClientes.addActionListener(e -> {
            new VentanaGestionClientes().setVisible(true);
            dispose();
        });
        btnGestionPedidos.addActionListener(e -> {
            new VentanaGestionPedidos().setVisible(true);
            dispose();
        });
//        btnControlInventario.addActionListener(e -> {
//            new VentanaControlInventario().setVisible(true);
//            dispose();
//        });
        btnReportesVentas.addActionListener(e -> {
            new VentanaReportesVentas().setVisible(true);
            dispose();
        });
        btnGestionEnvios.addActionListener(e -> {
            new VentanaGestionEnvios().setVisible(true);
            dispose();
        });
        btnVolver.addActionListener(e -> dispose());

        panel.add(btnGestionProductos);
        panel.add(btnGestionClientes);
        panel.add(btnGestionPedidos);
//        panel.add(btnControlInventario);
        panel.add(btnReportesVentas);
        panel.add(btnGestionEnvios);
        panel.add(btnVolver);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaAdministrador().setVisible(true));
    }
}

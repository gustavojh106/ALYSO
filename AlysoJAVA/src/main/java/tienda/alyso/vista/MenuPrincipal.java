package tienda.alyso.vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Tienda Alyso - Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAdministrador = new JButton("Administrador");
        btnAdministrador.addActionListener(e -> {
            VentanaAdministrador admin = new VentanaAdministrador();
            admin.setVisible(true);
        });

        JButton btnCliente = new JButton("Cliente");
        btnCliente.addActionListener(e -> {
            VentanaMenuCliente cliente = new VentanaMenuCliente();
            cliente.setVisible(true);
        });

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(btnAdministrador);
        panel.add(btnCliente);
        panel.add(btnSalir);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}

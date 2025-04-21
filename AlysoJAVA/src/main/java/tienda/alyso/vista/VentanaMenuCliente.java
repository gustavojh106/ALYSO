package tienda.alyso.vista;

import javax.swing.*;
import java.awt.*;

public class VentanaMenuCliente extends JFrame {
    public VentanaMenuCliente() {
        setTitle("Cliente - Menú");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnRegistrar = new JButton("Registrar Pedido");
        btnRegistrar.addActionListener(e -> {
            new VentanaRegistrarPedidoCliente().setVisible(true);
            dispose();
        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());

        panel.add(btnRegistrar);
        panel.add(btnVolver);

        add(panel);
    }
}

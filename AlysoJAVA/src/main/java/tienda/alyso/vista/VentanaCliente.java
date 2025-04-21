package tienda.alyso.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana para el rol Cliente
 */
public class VentanaCliente extends JFrame {

    public VentanaCliente() {
        setTitle("Cliente");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnRegistrarPedido = new JButton("Registrar Pedido");
        btnRegistrarPedido.addActionListener(e -> {
            VentanaRegistrarPedido ventana = new VentanaRegistrarPedido();
            ventana.setVisible(true);
        });

//        JButton btnVerPedidos = new JButton("Ver Mis Pedidos");
//        btnVerPedidos.addActionListener(e -> {
//            JOptionPane.showMessageDialog(this, "Funcionalidad de ver pedidos no implementada aún.");
//        });

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> this.dispose());

        panel.add(btnRegistrarPedido);
//        panel.add(btnVerPedidos);
        panel.add(btnVolver);

        add(panel);
    }
}

package tienda.alyso.vista;

import tienda.alyso.dao.PedidoDAO;
import tienda.alyso.modelo.Pedido;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaRegistrarPedido extends JFrame {

    private final JTextField txtIdPedido;
    private final JTextField txtIdCliente;
    private final JTextField txtFecha;
    private final JTextField txtTotal;
    private final JButton btnGuardar;

    public VentanaRegistrarPedido() {
        setTitle("Registrar Pedido");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtIdPedido = new JTextField();
        txtIdCliente = new JTextField();
        txtFecha = new JTextField();  // formato yyyy-MM-dd
        txtTotal = new JTextField();
        btnGuardar = new JButton("Guardar");

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("ID Pedido:"));
        panel.add(txtIdPedido);
        panel.add(new JLabel("ID Cliente:"));
        panel.add(txtIdCliente);
        panel.add(new JLabel("Fecha (yyyy-MM-dd):"));
        panel.add(txtFecha);
        panel.add(new JLabel("Total:"));
        panel.add(txtTotal);
        panel.add(new JLabel(""));
        panel.add(btnGuardar);

        add(panel);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPedido();
            }
        });
    }

    private void guardarPedido() {
    try {
        int idPedido = Integer.parseInt(txtIdPedido.getText());
        int idCliente = Integer.parseInt(txtIdCliente.getText());
        String fecha = txtFecha.getText();
        double total = Double.parseDouble(txtTotal.getText());

        Pedido nuevo = new Pedido(idPedido, idCliente, fecha, total);
        PedidoDAO dao = new PedidoDAO();
        boolean ok = dao.insertar(nuevo);

        if (ok) {
            JOptionPane.showMessageDialog(this, "✅ Pedido registrado correctamente");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se pudo registrar el pedido");
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "⚠️ Error de formato numérico: " + ex.getMessage());
    } catch (HeadlessException ex) {
        JOptionPane.showMessageDialog(this, "⚠️ Error: " + ex.getMessage());
    }
}


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


package tienda.alyso.vista;

import tienda.alyso.dao.ProductoDAO;
import tienda.alyso.modelo.Producto;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistrarProducto extends JFrame {

    private final JTextField txtId;
    private final JTextField txtNombre;
    private final JTextField txtPrecio;
    private final JTextField txtDescripcion;
    private final JButton btnGuardar;

    public VentanaRegistrarProducto() {
        setTitle("Registrar Producto");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtDescripcion = new JTextField();
        btnGuardar = new JButton("Guardar");

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("ID Producto:"));
        panel.add(txtId);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);
        panel.add(new JLabel(""));
        panel.add(btnGuardar);
        add(panel);

//        btnGuardar.addActionListener(e -> guardarProducto());
    }

    @SuppressWarnings("unchecked")
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

//    private void guardarProducto() {
//        try {
//            int id = Integer.parseInt(txtId.getText());
//            String nombre = txtNombre.getText();
//            double precio = Double.parseDouble(txtPrecio.getText());
//            String descripcion = txtDescripcion.getText();
//
//            Producto producto = new Producto(id, nombre, precio, descripcion);
//            ProductoDAO dao = new ProductoDAO();
//
//            if (dao.insertar(producto)) {
//                JOptionPane.showMessageDialog(this, "✅ Producto registrado correctamente");
//                dispose();
//            } else {
//                JOptionPane.showMessageDialog(this, "❌ No se pudo registrar el producto");
//            }
//
//        } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(this, "⚠️ ID o Precio inválido");
//        }
//    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables



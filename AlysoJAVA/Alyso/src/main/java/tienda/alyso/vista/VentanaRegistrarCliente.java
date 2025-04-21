package tienda.alyso.vista;

import tienda.alyso.dao.ClienteDAO;
import tienda.alyso.modelo.Cliente;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistrarCliente extends JFrame {

    private final JTextField txtId;
    private final JTextField txtApellido;
    private final JTextField txtNombre;  
    private final JTextField txtTelefono;
    private final JTextField txtCorreo;
    private final JButton btnGuardar;

    public VentanaRegistrarCliente() {
        setTitle("Registrar Cliente");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear campos
        txtId = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        btnGuardar = new JButton("Guardar");

        // Panel de formulario
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("ID Cliente:"));
        panel.add(txtId);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Correo:"));
        panel.add(txtCorreo);
        panel.add(new JLabel("")); // espacio vacío
        panel.add(btnGuardar);

        add(panel);

        // Acción del botón
        btnGuardar.addActionListener(e -> guardarCliente());
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

 private void guardarCliente() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();

            Cliente nuevo = new Cliente(id, nombre, apellido, telefono, correo);
            ClienteDAO dao = new ClienteDAO();
            boolean ok = dao.insertar(nuevo);

            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Cliente registrado correctamente");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo registrar el cliente");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ El ID debe ser un número entero");
        }
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


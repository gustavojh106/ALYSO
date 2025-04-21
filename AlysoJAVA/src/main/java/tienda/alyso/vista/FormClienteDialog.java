// FormClienteDialog.java
package tienda.alyso.vista;

import tienda.alyso.modelo.Cliente;
import javax.swing.*;
import java.awt.*;

public class FormClienteDialog extends JDialog {
    private final JTextField txtNombre   = new JTextField();
    private final JTextField txtApellido = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JTextField txtCorreo   = new JTextField();
    private boolean succeeded = false;

    public FormClienteDialog(Frame owner, Cliente cliente) {
        super(owner, true);
        setTitle(cliente == null ? "Agregar Cliente" : "Editar Cliente");
        setSize(350, 260);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Correo:"));
        panel.add(txtCorreo);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");
        panel.add(btnAceptar);
        panel.add(btnCancelar);
        add(panel);

        if (cliente != null) {
            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtTelefono.setText(cliente.getTelefono());
            txtCorreo.setText(cliente.getCorreo());
        }

        btnAceptar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() ||
                txtApellido.getText().trim().isEmpty() ||
                txtCorreo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Nombre, Apellido y Correo son obligatorios");
                return;
            }
            succeeded = true;
            dispose();
        });
        btnCancelar.addActionListener(e -> {
            succeeded = false;
            dispose();
        });
    }

    public boolean isSucceeded() { return succeeded; }
    public String getNombre()     { return txtNombre.getText().trim(); }
    public String getApellido()   { return txtApellido.getText().trim(); }
    public String getTelefono()   { return txtTelefono.getText().trim(); }
    public String getCorreo()     { return txtCorreo.getText().trim(); }
}

package tienda.alyso.vista;

import tienda.alyso.modelo.Producto;

import javax.swing.*;
import java.awt.*;

public class FormProductoDialog extends JDialog {
    private final JTextField txtNombre   = new JTextField();
    private final JTextField txtPrecio   = new JTextField();
    private final JTextField txtDescripcion = new JTextField();
    private boolean succeeded = false;

    public FormProductoDialog(Frame owner, Producto producto) {
        super(owner, true);
        setTitle(producto == null ? "Agregar Producto" : "Editar Producto");
        setSize(350, 220);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(btnAceptar);
        panel.add(btnCancelar);
        add(panel);

        // Si es edición, cargar valores
        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtDescripcion.setText(producto.getDescripcion());
        }

        btnAceptar.addActionListener(e -> {
            // validaciones sencillas
            if (txtNombre.getText().trim().isEmpty() ||
                txtPrecio.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Nombre y Precio son obligatorios");
                return;
            }
            try {
                Double.parseDouble(txtPrecio.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Precio inválido");
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

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public double getPrecio() {
        return Double.parseDouble(txtPrecio.getText().trim());
    }

    public String getDescripcion() {
        return txtDescripcion.getText().trim();
    }
}

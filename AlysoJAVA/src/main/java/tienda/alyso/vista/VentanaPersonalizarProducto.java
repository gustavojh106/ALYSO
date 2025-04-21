package tienda.alyso.vista;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class VentanaPersonalizarProducto extends JFrame {
    private String imagenPath = null;
    private String descripcion = "";
    private byte[] imagenData;  // guarda los bytes


    private final JLabel lblVista;
    private final JTextField txtDescripcion;

    public VentanaPersonalizarProducto(int productoId) {
        setTitle("Personalizar Producto ID " + productoId);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        lblVista = new JLabel();
        lblVista.setPreferredSize(new Dimension(200, 200));
        lblVista.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton btnCargar = new JButton("Seleccionar Imagen");
       btnCargar.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                try {
                    imagenData = Files.readAllBytes(f.toPath());
                    // Escalar para vista previa
                    ImageIcon icon = new ImageIcon(imagenData);
                    Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    lblVista.setIcon(new ImageIcon(img));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error leyendo imagen: " + ex.getMessage());
                }
            }
        });
        
        txtDescripcion = new JTextField();
        txtDescripcion.setBorder(BorderFactory.createTitledBorder("Descripción"));
        txtDescripcion.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) return;
                // Solo inserta si no superamos 30 caracteres
                if (getLength() + str.length() <= 30) {
                    super.insertString(offs, str, a);
                } else {
                    Toolkit.getDefaultToolkit().beep(); // opcional: señal sonora
                }
            }
        });
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> {
            descripcion = txtDescripcion.getText();
            dispose();
        });
        
        JPanel centro = new JPanel();
        centro.add(lblVista);
        centro.add(btnCargar);

        add(centro, BorderLayout.CENTER);
        add(txtDescripcion, BorderLayout.NORTH);
        add(btnAceptar, BorderLayout.SOUTH);
    }

    public String getImagenPath() {
        return imagenPath;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public byte[] getImagenData() {
        return imagenData;
    }
    
}

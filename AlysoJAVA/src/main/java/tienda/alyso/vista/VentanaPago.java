// VentanaPago.java
package tienda.alyso.vista;

import tienda.alyso.dao.ClienteDAO;
import tienda.alyso.dao.PedidoDAO;
import tienda.alyso.modelo.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class VentanaPago extends JFrame {

    private final List<VentanaRegistrarPedidoCliente.ItemPedido> items;
    private final Map<Integer, VentanaRegistrarPedidoCliente.Personalizacion> personalizaciones;
    private final double total;

    public VentanaPago(List<VentanaRegistrarPedidoCliente.ItemPedido> items,
                       Map<Integer, VentanaRegistrarPedidoCliente.Personalizacion> personalizaciones) {
        this.items = items;
        this.personalizaciones = personalizaciones;
        // Calculamos total
        this.total = items.stream()
                .mapToDouble(i -> i.getCantidad() * i.getPrecioUnitario())
                .sum();

        setTitle("Pago – Total: "  + "₡"+ total);
        setSize(400, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Total a pagar: " + "₡" + total));
        panel.add(new JLabel("Correo Electrónico:"));

        JTextField txtEmail = new JTextField();
        panel.add(txtEmail);

        JButton btnVerificar = new JButton("Verificar Cliente");
        JButton btnPagar     = new JButton("Pagar");
        btnPagar.setEnabled(false);

        // 1) Verificamos que el cliente exista
        btnVerificar.addActionListener(e -> {
            Cliente c = new ClienteDAO().buscarPorEmail(txtEmail.getText().trim());
            if (c != null) {
                btnPagar.setEnabled(true);
                JOptionPane.showMessageDialog(this,
                    "Cliente: " + c.getNombre() + " " + c.getApellido());
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no existe");
            }
        });

        // 2) Al pagar, insertamos pedido, productos y diseños
        btnPagar.addActionListener(e -> {
            Cliente c = new ClienteDAO().buscarPorEmail(txtEmail.getText().trim());
            if (c == null) {
                JOptionPane.showMessageDialog(this, "Cliente no existe");
                return;
            }

            PedidoDAO pdao = new PedidoDAO();
            // Inserta PEDIDO
            int idPedido = pdao.insertarPedido(c.getIdCliente(), total);
            if (idPedido <= 0) {
                JOptionPane.showMessageDialog(this, "Error creando pedido");
                return;
            }

            boolean ok = true;
            // Inserta cada PEDIDO_PRODUCTO
            for (var item : items) {
                boolean resPP = pdao.insertarPedidoProducto(
                    idPedido,
                    item.getIdProducto(),
                    item.getCantidad(),
                    item.getPrecioUnitario(),
                    item.isPersonalizado()
                );
                if (!resPP) ok = false;

                // Si hubo personalización, la guardamos también
                if (item.isPersonalizado()) {
                    var pz = personalizaciones.get(item.getIdProducto());
                    int idDis = pdao.insertarDisenoPersonalizado(
                        idPedido,
                        pz.getDescripcion(),
                        pz.getImagenData()
                    );
                    if (idDis <= 0) ok = false;
                }
            }

            if (ok) {
                JOptionPane.showMessageDialog(this,
                    "¡Pago y registro completo! Pedido ID: " + idPedido);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Pedido creado, pero hubo errores en algunos registros");
            }
        });

        panel.add(btnVerificar);
        panel.add(btnPagar);
        add(panel);
    }
}

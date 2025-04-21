// VentanaRegistrarPedidoCliente.java
package tienda.alyso.vista;

import tienda.alyso.dao.ProductoDAO;
import tienda.alyso.modelo.Producto;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentanaRegistrarPedidoCliente extends JFrame {

    private final Map<Integer, JSpinner> spinnerMap   = new HashMap<>();
    public  final Map<Integer, Personalizacion> personalizaciones = new HashMap<>();
    private final Map<Integer, Producto> productoMap  = new HashMap<>();
    private final JButton btnContinuar;

    public VentanaRegistrarPedidoCliente() {
        setTitle("Registrar Pedido - Selección de Productos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        List<Producto> productos = new ProductoDAO().listar();

        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));

        for (Producto p : productos) {
            productoMap.put(p.getIdProducto(), p);

            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
            JLabel lbl = new JLabel(p.getNombre() + " (" + p.getPrecio() + "₡)");
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
            spinnerMap.put(p.getIdProducto(), spinner);

            JButton btnPersonalizar = new JButton("Personalizar");
            btnPersonalizar.addActionListener(e -> {
                VentanaPersonalizarProducto vp = new VentanaPersonalizarProducto(p.getIdProducto());
                vp.setVisible(true);
                vp.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent evt) {
                        personalizaciones.put(p.getIdProducto(),
                            new Personalizacion(vp.getImagenData(), vp.getDescripcion()));
                    }
                });
            });

            spinner.addChangeListener(e -> updateContinuar());

            fila.add(lbl);
            fila.add(new JLabel("Cantidad:"));
            fila.add(spinner);
            fila.add(btnPersonalizar);
            listaPanel.add(fila);
        }

        btnContinuar = new JButton("Continuar");
        btnContinuar.setEnabled(false);
        btnContinuar.addActionListener(e -> {
            List<ItemPedido> items = getSelectedItems();
            new VentanaPago(items, personalizaciones).setVisible(true);
            dispose();
        });

        add(new JScrollPane(listaPanel), BorderLayout.CENTER);
        add(btnContinuar, BorderLayout.SOUTH);
    }

    private void updateContinuar() {
        for (JSpinner sp : spinnerMap.values()) {
            if ((int) sp.getValue() > 0) {
                btnContinuar.setEnabled(true);
                return;
            }
        }
        btnContinuar.setEnabled(false);
    }

    private List<ItemPedido> getSelectedItems() {
        List<ItemPedido> lista = new ArrayList<>();
        for (Map.Entry<Integer, JSpinner> e : spinnerMap.entrySet()) {
            int id  = e.getKey();
            int qty = (int) e.getValue().getValue();
            if (qty > 0) {
                Producto p = productoMap.get(id);
                boolean pers = personalizaciones.containsKey(id);
                lista.add(new ItemPedido(
                    id,
                    p.getNombre(),
                    qty,
                    p.getPrecio(),
                    pers
                ));
            }
        }
        return lista;
    }

public static class Personalizacion {
    private final byte[] imagenData;
    private final String descripcion;
    public Personalizacion(byte[] img, String desc) {
        this.imagenData = img;
        this.descripcion = desc;
    }
    public byte[] getImagenData() { return imagenData; }
    public String getDescripcion() { return descripcion; }
}


    public static class ItemPedido {
        private final int idProducto, cantidad;
        private final String nombre;
        private final double precioUnitario;
        private final boolean personalizado;

        public ItemPedido(int idProducto, String nombre, int cantidad,
                          double precioUnitario, boolean personalizado) {
            this.idProducto     = idProducto;
            this.nombre         = nombre;
            this.cantidad       = cantidad;
            this.precioUnitario = precioUnitario;
            this.personalizado  = personalizado;
        }
        public int getIdProducto()       { return idProducto; }
        public String getNombre()        { return nombre; }
        public int getCantidad()         { return cantidad; }
        public double getPrecioUnitario(){ return precioUnitario; }
        public boolean isPersonalizado(){ return personalizado; }
    }
}

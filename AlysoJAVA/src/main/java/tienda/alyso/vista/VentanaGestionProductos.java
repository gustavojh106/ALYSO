// VentanaGestionProductos.java
package tienda.alyso.vista;

import tienda.alyso.dao.ProductoDAO;
import tienda.alyso.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionProductos extends JFrame {
    private final ProductoDAO dao = new ProductoDAO();
    private final DefaultTableModel model;
    private final JTable tabla;

    public VentanaGestionProductos() {
        setTitle("Gestión de Productos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Modelo de la tabla incluyendo STOCK
        String[] cols = { "ID", "Nombre", "Precio", "Descripción", "Stock" };
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla = new JTable(model);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botones CRUD + manejo de stock
        JButton btnAgregar     = new JButton("Agregar");
        JButton btnEditar      = new JButton("Editar");
        JButton btnEliminar    = new JButton("Eliminar");
        JButton btnAgregarStock= new JButton("Agregar Stock");
        JButton btnRestarStock = new JButton("Restar Stock");
        JButton btnRefrescar   = new JButton("Refrescar");

        JPanel pnlBotones = new JPanel();
        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnEditar);
        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnAgregarStock);
        pnlBotones.add(btnRestarStock);
        pnlBotones.add(btnRefrescar);
        add(pnlBotones, BorderLayout.SOUTH);

        // Carga inicial
        cargarDatos();

        // Acción Agregar
        btnAgregar.addActionListener(e -> {
            FormProductoDialog dlg = new FormProductoDialog(this, null);
            dlg.setVisible(true);
            if (dlg.isSucceeded()) {
                Producto p = new Producto(
                    0,
                    dlg.getNombre(),
                    dlg.getPrecio(),
                    dlg.getDescripcion(),
                    0
                );
                int newId = dao.insertarProducto(p);
                if (newId > 0) {
                    p.setIdProducto(newId);
                    JOptionPane.showMessageDialog(
                        this,
                        "El producto ha sido agregado correctamente al catálogo.",
                        "Producto agregado",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Ocurrió un error al agregar el producto.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Acción Editar
        btnEditar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto");
                return;
            }
            int id        = (int) model.getValueAt(row, 0);
            String nom    = (String) model.getValueAt(row, 1);
            double pre    = (double) model.getValueAt(row, 2);
            String desc   = (String) model.getValueAt(row, 3);
            int stock     = (int) model.getValueAt(row, 4);

            Producto p = new Producto(id, nom, pre, desc, stock);
            FormProductoDialog dlg = new FormProductoDialog(this, p);
            dlg.setVisible(true);
            if (dlg.isSucceeded()) {
                p.setNombre(dlg.getNombre());
                p.setPrecio(dlg.getPrecio());
                p.setDescripcion(dlg.getDescripcion());
                if (dao.actualizarProducto(p)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "El producto ha sido actualizado correctamente.",
                        "Producto actualizado",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Ocurrió un error al actualizar el producto.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Acción Eliminar
        btnEliminar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            int option = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro(a) de que desea eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                if (dao.eliminarProducto(id)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "El producto ha sido eliminado correctamente del catálogo.",
                        "Producto eliminado",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Ocurrió un error al eliminar el producto.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Acción Agregar Stock
        btnAgregarStock.addActionListener(e -> ajustarStock(true));

        // Acción Restar Stock
        btnRestarStock.addActionListener(e -> ajustarStock(false));

        // Acción Refrescar
        btnRefrescar.addActionListener(e -> cargarDatos());
    }

    /** Ajusta el stock: si esAgregar=true suma, sino resta */
    private void ajustarStock(boolean esAgregar) {
        int row = tabla.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        String msg = esAgregar
            ? "Ingrese unidades a agregar:"
            : "Ingrese unidades a restar:";
        String input = JOptionPane.showInputDialog(this, msg);
        if (input != null) {
            try {
                int qty = Integer.parseInt(input.trim());
                if (qty < 0) throw new NumberFormatException();
                boolean ok = dao.actualizarStock(id, esAgregar ? qty : -qty);
                if (ok) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Stock actualizado correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "Error al actualizar stock.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Cantidad inválida.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    /** Trae del DAO y repuebla la tabla, ahora con stock */
    private void cargarDatos() {
        model.setRowCount(0);
        List<Producto> lista = dao.listar();
        for (Producto p : lista) {
            model.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getPrecio(),
                p.getDescripcion(),
                p.getStock()
            });
        }
    }
}

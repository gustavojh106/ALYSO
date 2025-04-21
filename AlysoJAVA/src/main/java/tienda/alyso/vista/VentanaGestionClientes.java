// VentanaGestionClientes.java
package tienda.alyso.vista;

import tienda.alyso.dao.ClienteDAO;
import tienda.alyso.modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionClientes extends JFrame {
    private final ClienteDAO dao = new ClienteDAO();
    private final DefaultTableModel model;
    private final JTable tabla;

    public VentanaGestionClientes() {
        setTitle("Gestión de Clientes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] cols = { "ID", "Nombre", "Apellido", "Teléfono", "Correo" };
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(model);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnAgregar   = new JButton("Agregar");
        JButton btnEditar    = new JButton("Editar");
        JButton btnEliminar  = new JButton("Eliminar");
        JButton btnHistorial = new JButton("Historial");
        JButton btnRefrescar = new JButton("Refrescar");

        JPanel pnl = new JPanel();
        pnl.add(btnAgregar);
        pnl.add(btnEditar);
        pnl.add(btnEliminar);
        pnl.add(btnHistorial);
        pnl.add(btnRefrescar);
        add(pnl, BorderLayout.SOUTH);

        cargarDatos();

        btnAgregar.addActionListener(e -> {
            FormClienteDialog dlg = new FormClienteDialog(this, null);
            dlg.setVisible(true);
            if (dlg.isSucceeded()) {
                Cliente c = new Cliente(
                    dlg.getNombre(),
                    dlg.getApellido(),
                    dlg.getTelefono(),
                    dlg.getCorreo()
                );
                if (dao.insertar(c)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "El cliente ha sido agregado correctamente.",
                        "Cliente agregado",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarDatos();
                }
            }
        });

        btnEditar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un cliente");
                return;
            }
            Cliente c = new Cliente();
            c.setIdCliente((int) model.getValueAt(row, 0));
            c.setNombre((String) model.getValueAt(row, 1));
            c.setApellido((String) model.getValueAt(row, 2));
            c.setTelefono((String) model.getValueAt(row, 3));
            c.setCorreo((String) model.getValueAt(row, 4));

            FormClienteDialog dlg = new FormClienteDialog(this, c);
            dlg.setVisible(true);
            if (dlg.isSucceeded()) {
                c.setNombre(dlg.getNombre());
                c.setApellido(dlg.getApellido());
                c.setTelefono(dlg.getTelefono());
                c.setCorreo(dlg.getCorreo());
                if (dao.actualizar(c)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "El cliente ha sido actualizado correctamente.",
                        "Cliente actualizado",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarDatos();
                }
            }
        });

        btnEliminar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un cliente");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            int opt = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro(a) de que desea eliminar este cliente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );
            if (opt == JOptionPane.YES_OPTION && dao.eliminar(id)) {
                JOptionPane.showMessageDialog(
                    this,
                    "El cliente ha sido eliminado correctamente.",
                    "Cliente eliminado",
                    JOptionPane.INFORMATION_MESSAGE
                );
                cargarDatos();
            }
        });

        btnHistorial.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un cliente");
                return;
            }
            int id = (int) model.getValueAt(row, 0);
            new HistorialComprasDialog(this, id).setVisible(true);
        });

        btnRefrescar.addActionListener(e -> cargarDatos());
    }

    private void cargarDatos() {
        model.setRowCount(0);
        List<Cliente> lista = dao.listar();
        for (Cliente c : lista) {
            model.addRow(new Object[]{
                c.getIdCliente(),
                c.getNombre(),
                c.getApellido(),
                c.getTelefono(),
                c.getCorreo()
            });
        }
    }
}

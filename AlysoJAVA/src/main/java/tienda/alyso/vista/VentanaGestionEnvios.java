// VentanaGestionEnvios.java
package tienda.alyso.vista;

import tienda.alyso.dao.EnvioDAO;
import tienda.alyso.modelo.Envio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionEnvios extends JFrame {
    private final EnvioDAO dao = new EnvioDAO();
    private final DefaultTableModel model;
    private final JTable tabla;

    public VentanaGestionEnvios() {
        setTitle("Gestión de Envíos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Columnas de la tabla
        String[] cols = { "ID Envío", "ID Pedido", "Fecha", "Empresa", "Estado", "Detalle" };
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(model);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Botones de control
        JButton btnActualizar = new JButton("Actualizar Seguimiento");
        JButton btnEliminar   = new JButton("Eliminar Envío");
        JButton btnRefrescar  = new JButton("Refrescar");

        JPanel pnl = new JPanel();
        pnl.add(btnActualizar);
        pnl.add(btnEliminar);
        pnl.add(btnRefrescar);
        add(pnl, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> cargarDatos());
        btnEliminar.addActionListener(e -> eliminarEnvio());
        btnActualizar.addActionListener(e -> actualizarSeguimiento());

        // Carga inicial
        cargarDatos();
    }

    private void cargarDatos() {
        model.setRowCount(0);
        List<Envio> lista = dao.listarEnvíos();
        for (Envio en : lista) {
            model.addRow(new Object[]{
                en.getIdEnvio(),
                en.getIdPedido(),
                en.getFechaEnvio(),
                en.getEmpresa(),
                en.getEstadoEnvio(),
                en.getDetalle()
            });
        }
    }

    private void eliminarEnvio() {
        int row = tabla.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un envío");
            return;
        }
        int id = (int)model.getValueAt(row, 0);
        int opt = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro(a) de que desea eliminar este envío?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        if (opt == JOptionPane.YES_OPTION && dao.eliminarEnvio(id)) {
            JOptionPane.showMessageDialog(
                this,
                "Envío eliminado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            cargarDatos();
        }
    }

    private void actualizarSeguimiento() {
        int row = tabla.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un envío");
            return;
        }
        int idEnvio = (int)model.getValueAt(row, 0);
        String nuevoEstado = JOptionPane.showInputDialog(
            this,
            "Nuevo estado:",
            model.getValueAt(row, 4)
        );
        if (nuevoEstado == null) return;
        String detalle = JOptionPane.showInputDialog(
            this,
            "Detalle de seguimiento:",
            model.getValueAt(row, 5)
        );
        if (detalle == null) return;

        if (dao.actualizarEnvio(idEnvio, nuevoEstado.trim(), detalle.trim())) {
            JOptionPane.showMessageDialog(
                this,
                "Seguimiento actualizado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Error al actualizar el envío.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

package tienda.alyso.vista;

import tienda.alyso.dao.PedidoDAO;
import tienda.alyso.modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaGestionPedidos extends JFrame {
    private final PedidoDAO dao = new PedidoDAO();
    private final DefaultTableModel model;
    private final JTable tabla;

    public VentanaGestionPedidos() {
        setTitle("Gestión de Pedidos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Columnas: ID, Fecha, Total, Estado, ClienteID
        String[] cols = { "ID", "Fecha", "Total", "Estado", "ClienteID" };
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(model);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnCambiarEstado = new JButton("Cambiar Estado");
        JButton btnVerEtiqueta    = new JButton("Ver Etiqueta");
        JButton btnEliminar       = new JButton("Eliminar Pedido");
        JButton btnRefrescar      = new JButton("Refrescar");

        JPanel pnl = new JPanel();
        pnl.add(btnCambiarEstado);
        pnl.add(btnVerEtiqueta);
        pnl.add(btnEliminar);
        pnl.add(btnRefrescar);
        add(pnl, BorderLayout.SOUTH);

        cargarDatos();

        // Cambiar Estado
        btnCambiarEstado.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Selecciona un pedido"); return; }
            int id = (int) model.getValueAt(row, 0);

            String[] opciones = { "PENDIENTE","PROCESADO","ENVIADO","ENTREGADO" };
            String nuevo = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione nuevo estado:",
                "Cambiar Estado",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                model.getValueAt(row, 3)
            );
            if (nuevo != null) {
                if (dao.actualizarEstadoPedido(id, nuevo)) {
                    JOptionPane.showMessageDialog(this,
                        "Estado actualizado a '" + nuevo + "'.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                }
            }
        });

        // Ver Etiqueta
        btnVerEtiqueta.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Selecciona un pedido"); return; }
            int id = (int) model.getValueAt(row, 0);
            String etiqueta = dao.generarEtiquetaEnvio(id);

            JTextArea tx = new JTextArea(etiqueta);
            tx.setEditable(false);
            JScrollPane sp = new JScrollPane(tx);
            sp.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(
                this,
                sp,
                "Etiqueta de Envío - Pedido " + id,
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Eliminar Pedido
        btnEliminar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Selecciona un pedido"); return; }
            int id = (int) model.getValueAt(row, 0);

            int opt = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro(a) de que desea eliminar este pedido?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );
            if (opt == JOptionPane.YES_OPTION && dao.eliminarPedido(id)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado correctamente.",
                    "Eliminado",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            }
        });

        btnRefrescar.addActionListener(e -> cargarDatos());
    }

    private void cargarDatos() {
        model.setRowCount(0);
        List<Pedido> lista = dao.listarTodos();  // asume que listas todos los pedidos
        for (Pedido p : lista) {
            model.addRow(new Object[]{
                p.getIdPedido(),
                p.getFecha(),
                p.getTotal(),
                p.getEstado(),
                p.getIdCliente()
            });
        }
    }
}

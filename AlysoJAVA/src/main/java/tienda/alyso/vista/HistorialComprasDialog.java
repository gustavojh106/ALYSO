// HistorialComprasDialog.java
package tienda.alyso.vista;

import tienda.alyso.dao.PedidoDAO;
import tienda.alyso.modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistorialComprasDialog extends JDialog {
    public HistorialComprasDialog(Frame owner, int idCliente) {
        super(owner, true);
        setTitle("Historial de Compras - Cliente " + idCliente);
        setSize(500, 300);
        setLocationRelativeTo(owner);

        String[] cols = { "ID Pedido", "Fecha", "Total" };
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(model);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Cargar datos
        List<Pedido> pedidos = new PedidoDAO().listarPorCliente(idCliente);
        for (Pedido p : pedidos) {
            model.addRow(new Object[]{ p.getIdPedido(), p.getFecha(), p.getTotal() });
        }

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel pnl = new JPanel();
        pnl.add(btnCerrar);
        add(pnl, BorderLayout.SOUTH);
    }
}

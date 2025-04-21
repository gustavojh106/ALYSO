// VentanaReportesVentas.java
package tienda.alyso.vista;

import tienda.alyso.dao.ReportesDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaReportesVentas extends JFrame {
    private final ReportesDAO dao = new ReportesDAO();
    private final JComboBox<String> cbReportes;
    private final JTextField txtEmail;
    private final JButton btnGenerar;
    private final DefaultTableModel model;
    private final JTable tabla;

    public VentanaReportesVentas() {
        setTitle("Generación de Reportes de Ventas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel superior con filtro y selector
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        String[] reportes = {
            "Productos vendidos último mes",
            "Clientes con más pedidos",
            "Clientes con mayor valor de pedidos",
            "Ventas por cliente"
        };
        cbReportes = new JComboBox<>(reportes);
        txtEmail   = new JTextField(20);
        txtEmail.setEnabled(false);
        btnGenerar = new JButton("Generar");
        pnlTop.add(new JLabel("Reporte:"));
        pnlTop.add(cbReportes);
        pnlTop.add(new JLabel("Filtrar por correo:"));
        pnlTop.add(txtEmail);
        pnlTop.add(btnGenerar);
        add(pnlTop, BorderLayout.NORTH);

        // Tabla para mostrar resultados
        model = new DefaultTableModel();
        tabla = new JTable(model);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Habilitar email solo para el último reporte
        cbReportes.addActionListener(e -> {
            txtEmail.setEnabled(cbReportes.getSelectedIndex() == 3);
        });

        // Al hacer clic en Generar
        btnGenerar.addActionListener(e -> generarReporte());

        // Mostrar ventana
        setVisible(true);
    }

    private void generarReporte() {
        int idx = cbReportes.getSelectedIndex();
        model.setRowCount(0);
        model.setColumnCount(0);

        switch (idx) {
            case 0 -> { // Productos vendidos último mes
                model.setColumnIdentifiers(new String[]{
                    "ID Producto", "Nombre", "Total Vendido"
                });
                List<Object[]> data = dao.productosVendidosUltimoMes(
                    txtEmail.isEnabled() && !txtEmail.getText().isBlank()
                        ? txtEmail.getText().trim()
                        : null
                );
                for (Object[] row : data) model.addRow(row);
            }
            case 1 -> { // Clientes con más pedidos
                model.setColumnIdentifiers(new String[]{
                    "ID Cliente", "Nombre", "Apellido", "# Pedidos"
                });
                dao.topClientesPorPedidos()
                   .forEach(row -> model.addRow(row));
            }
            case 2 -> { // Clientes con mayor valor de pedidos
                model.setColumnIdentifiers(new String[]{
                    "ID Cliente", "Nombre", "Apellido", "Total Ventas"
                });
                dao.topClientesPorValor()
                   .forEach(row -> model.addRow(row));
            }
            case 3 -> { // Ventas por cliente (por correo)
                String email = txtEmail.getText().trim();
                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Por favor ingresa un correo para filtrar.",
                        "Filtro requerido",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                model.setColumnIdentifiers(new String[]{
                    "ID Pedido", "Fecha", "Total", "Estado"
                });
                dao.ventasPorCliente(email)
                   .forEach(row -> model.addRow(row));
            }
        }
    }
}

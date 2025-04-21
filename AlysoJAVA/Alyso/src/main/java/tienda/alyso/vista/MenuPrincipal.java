package tienda.alyso.vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends javax.swing.JFrame {

public MenuPrincipal() {
        setTitle("Tienda Alyso - Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel con botones
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        JButton btnRegistrar = new JButton("Registrar Cliente");
        btnRegistrar.addActionListener(e -> {
        VentanaRegistrarCliente ventana = new VentanaRegistrarCliente();
        ventana.setVisible(true);
});
        
        JButton btnBuscar = new JButton("Buscar Cliente");
        JButton btnListar = new JButton("Ver Todos los Clientes");
        JButton btnEditar = new JButton("Editar Cliente");
        JButton btnEliminar = new JButton("Eliminar Cliente");
        JButton btnRegistrarProducto = new JButton("Registrar Producto");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnRegistrar);
        panel.add(btnBuscar);
        panel.add(btnListar);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnRegistrarProducto);
        panel.add(btnSalir);
        

        add(panel);
        
        btnRegistrarProducto.addActionListener(e -> {
    VentanaRegistrarProducto ventana = new VentanaRegistrarProducto();
    ventana.setVisible(true);
});

        // Acción de salida
        btnSalir.addActionListener(e -> System.exit(0));
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

  public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.condominio_vistaverde;

import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.time.Month;

public class CasasMorosas extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CasasMorosas.class.getName());

    /**
     * Creates new form CASAS_MOROSAS
     */
    public CasasMorosas() {
        initComponents();
                this.setSize(900, 681);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    cargarCasasMorosas();
    cargarMesActual();
    tblMorosos.setDefaultEditor(Object.class, null);
    }
    
    private void cargarMesActual() {

    LocalDate fecha = LocalDate.now();

    String mes = fecha.getMonth()
            .getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

    int año = fecha.getYear();

    lblMes.setText(
        mes.substring(0,1).toUpperCase()
        + mes.substring(1)
        + " " + año
    );
}
    
private void cargarCasasMorosas() {

    DefaultTableModel modelo =
            (DefaultTableModel) tblMorosos.getModel();

    modelo.setRowCount(0);

    int totalMorosos = 0;

    try {

        org.w3c.dom.Document doc = BDXML.obtenerDocumento();

        if (doc == null) {
            return;
        }

        java.time.LocalDate fecha = java.time.LocalDate.now();

        String mesActual = fecha.getMonth()
                .getDisplayName(
                        java.time.format.TextStyle.FULL,
                        new java.util.Locale("es", "ES")
                );

        mesActual = mesActual.substring(0,1).toUpperCase()
                + mesActual.substring(1);

        String añoActual = String.valueOf(fecha.getYear());

        org.w3c.dom.NodeList listaCasas =
                doc.getElementsByTagName("casa");

        for (int i = 0; i < listaCasas.getLength(); i++) {

            org.w3c.dom.Element casa =
                    (org.w3c.dom.Element) listaCasas.item(i);

            String numeroCasa =
                    casa.getAttribute("numero");

            String nombre = "Sin propietario";
            String telefono = "-";

            org.w3c.dom.NodeList propietarios =
                    casa.getElementsByTagName("propietario");

            if (propietarios.getLength() > 0) {

                org.w3c.dom.Element prop =
                        (org.w3c.dom.Element) propietarios.item(0);

                nombre = prop.getElementsByTagName("nombre")
                        .item(0)
                        .getTextContent();

                telefono = prop.getElementsByTagName("telefono")
                        .item(0)
                        .getTextContent();
            }

            boolean pagó = BDXML.existePago(
                    numeroCasa,
                    mesActual,
                    añoActual
            );

            if (!pagó) {

                modelo.addRow(new Object[]{
                    numeroCasa,
                    nombre,
                    telefono,
                    mesActual + " " + añoActual
                });

                totalMorosos++;
            }
        }

        lblTotalMorosos.setText(
                "Total casas morosas: " + totalMorosos
        );

    } catch (Exception e) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Error cargando casas morosas: "
                + e.getMessage()
        );

        e.printStackTrace();
    }
}   


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        scrollMorosos = new javax.swing.JScrollPane();
        tblMorosos = new javax.swing.JTable();
        lblMesActual = new javax.swing.JLabel();
        lblMes = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        lblTotalMorosos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Casas Morosas");

        jButton2.setText("Volver a Menú");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        lblTitulo.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 36)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("CASAS MOROSAS");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblDescripcion.setText("Casas que no han pagado el mes actual");

        tblMorosos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No. de Casa", "Propietario", "Teléfono", "Mes Pendiente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollMorosos.setViewportView(tblMorosos);

        lblMesActual.setText("Mes actual:");

        lblMes.setText("Mayo 2026");

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        lblTotalMorosos.setText("Total casas morosas: 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(lblDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMesActual, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblMes, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(79, 79, 79))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalMorosos, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(scrollMorosos, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(64, 64, 64))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(171, 171, 171)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMesActual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMes))
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDescripcion)
                .addGap(31, 31, 31)
                .addComponent(scrollMorosos, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTotalMorosos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        MenuPrincipal menu = new MenuPrincipal ();
        menu.setVisible(true);
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        cargarCasasMorosas();
    }//GEN-LAST:event_btnActualizarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new CasasMorosas().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblMes;
    private javax.swing.JLabel lblMesActual;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalMorosos;
    private javax.swing.JScrollPane scrollMorosos;
    private javax.swing.JTable tblMorosos;
    // End of variables declaration//GEN-END:variables
}

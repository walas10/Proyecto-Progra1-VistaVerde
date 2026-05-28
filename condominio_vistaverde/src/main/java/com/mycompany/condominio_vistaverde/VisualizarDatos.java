package com.mycompany.condominio_vistaverde;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class VisualizarDatos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VisualizarDatos.class.getName());

    /**
     * Creates new form VisualizarDatos
     */
    public VisualizarDatos() {
       initComponents();

    cargarTablaCompleta();
    }

    public void cargarTablaCompleta() {

    try {

        DefaultTableModel modelo =
                new DefaultTableModel();

        modelo.addColumn("Casa");
        modelo.addColumn("Propietario");
        modelo.addColumn("Telefono");
        modelo.addColumn("Correo");
        modelo.addColumn("Mes");
        modelo.addColumn("Año");
        modelo.addColumn("Cuota");

        Document doc = BDXML.obtenerDocumento();

        NodeList listaPagos =
                doc.getElementsByTagName("pago");

        for (int i = 0; i < listaPagos.getLength(); i++) {

            Element pago =
                    (Element) listaPagos.item(i);

            String casa =
                    pago.getElementsByTagName("casa")
                            .item(0).getTextContent();

            String mes =
                    pago.getElementsByTagName("mes")
                            .item(0).getTextContent();

            String año =
                    pago.getElementsByTagName("año")
                            .item(0).getTextContent();

            String cuota =
                    pago.getElementsByTagName("cuota")
                            .item(0).getTextContent();

            String numeroCasa =
                    casa.replace("CASA ", "");

            String nombre = "";
            String telefono = "";
            String correo = "";

            NodeList listaCasas =
                    doc.getElementsByTagName("casa");

            for (int j = 0; j < listaCasas.getLength(); j++) {

                Element casaXML =
                        (Element) listaCasas.item(j);

                if (casaXML.hasAttribute("numero")) {

                    if (casaXML.getAttribute("numero")
                            .equals(numeroCasa)) {

                        NodeList propietarios =
                                casaXML.getElementsByTagName("propietario");

                        if (propietarios.getLength() > 0) {

                            Element prop =
                                    (Element) propietarios.item(0);

                            nombre =
                                    prop.getElementsByTagName("nombre")
                                            .item(0).getTextContent();

                            telefono =
                                    prop.getElementsByTagName("telefono")
                                            .item(0).getTextContent();

                            correo =
                                    prop.getElementsByTagName("correo")
                                            .item(0).getTextContent();
                        }
                    }
                }
            }

            modelo.addRow(new Object[]{
                casa,
                nombre,
                telefono,
                correo,
                mes,
                año,
                cuota
            });
        }

        jTable1.setModel(modelo);

    } catch (DOMException e) {

        JOptionPane.showMessageDialog(null,
                "Error cargando tabla: "
                + e.getMessage());
    }
}

    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Borrar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setBackground(new java.awt.Color(0, 0, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jButton3.setText("MENU");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
 try {

        int fila =
                jTable1.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(null,
                    "Seleccione una fila");

            return;
        }

        Document doc =
                BDXML.obtenerDocumento();

        NodeList listaPagos =
                doc.getElementsByTagName("pago");

        Node nodo =
                listaPagos.item(fila);

        nodo.getParentNode()
                .removeChild(nodo);

        BDXML.guardarDocumento(doc);

        JOptionPane.showMessageDialog(null,
                "Pago eliminado");

        cargarTablaCompleta();

    } catch (HeadlessException | DOMException e) {

        JOptionPane.showMessageDialog(null,
                "Error: " + e.getMessage());
    }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        MenuPrincipal menu = new MenuPrincipal ();
        menu.setVisible(true);
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new VisualizarDatos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

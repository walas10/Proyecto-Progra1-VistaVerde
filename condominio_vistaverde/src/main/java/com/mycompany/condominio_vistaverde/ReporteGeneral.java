package com.mycompany.condominio_vistaverde;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.time.LocalDate;
import java.util.Date;

public class ReporteGeneral extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReporteGeneral.class.getName());

    public ReporteGeneral() {
        initComponents();
    this.setSize(900, 681);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    
    
    cargarReporteGeneral();
    }
    
    private double calcularTotalRecaudadoMes(Document doc, String mesActual, String anioActual) {
    double total = 0;

    NodeList listaPagos = doc.getElementsByTagName("pago");

    for (int i = 0; i < listaPagos.getLength(); i++) {
        Element pago = (Element) listaPagos.item(i);

        String mes = obtenerTexto(pago, "mes");
        String anio = obtenerTexto(pago, "año");
        String cuotaTexto = obtenerTexto(pago, "cuota");

        if (mes.equals(mesActual) && anio.equals(anioActual)) {
            try {
                total += Double.parseDouble(cuotaTexto);
            } catch (NumberFormatException e) {
                System.out.println("Error convirtiendo cuota: " + cuotaTexto);
            }
        }
    }

    return total;
}
   
    private void cargarReporteGeneral() {
         Document doc = BDXML.obtenerDocumento();

    if (doc == null) {
        JOptionPane.showMessageDialog(this, "No se pudo cargar el archivo residencial.xml");
        return;
    }

    LocalDate fechaActual = LocalDate.now();
    String mesActual = obtenerNombreMes(fechaActual.getMonthValue());
    String anioActual = String.valueOf(fechaActual.getYear());

    double cuotaActual = Double.parseDouble(BDXML.obtenerCuotaActual());
    double totalRecaudadoMes = calcularTotalRecaudadoMes(doc, mesActual, anioActual);
    double totalEsperadoMes = cuotaActual * 30;

    lblMesActual.setText("Mes Actual: " + mesActual + " " + anioActual);

    DefaultTableModel modelo = new DefaultTableModel(
        new Object[]{"Número de casa", "Nombre del propietario", "Estado del mes actual", "Total pagado en el año"},
        0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    for (int i = 1; i <= 30; i++) {
        String numeroCasa = "CASA " + i;

        String propietario = obtenerPropietario(doc, numeroCasa);
        boolean pagoMesActual = existePagoMesActual(doc, numeroCasa, mesActual, anioActual);
        double totalPagadoAnio = calcularTotalPagadoAnio(doc, numeroCasa, anioActual);

        String estado = pagoMesActual ? "Pagado" : "Pendiente";

        modelo.addRow(new Object[]{
            numeroCasa,
            propietario,
            estado,
            "Q. " + String.format("%.2f", totalPagadoAnio)
        });
    }

    tblReporteGeneral.setModel(modelo);
    tblReporteGeneral.setRowHeight(25);

    lblResumenMes.setText(
        "Recaudado este mes: Q. " + String.format("%.2f", totalRecaudadoMes)
        + " / Esperado: Q. " + String.format("%.2f", totalEsperadoMes)
    );
    }
    
    
    private String obtenerPropietario(Document doc, String casaBuscada) {
    NodeList listaCasas = doc.getElementsByTagName("casa");

    for (int i = 0; i < listaCasas.getLength(); i++) {
        Element casaElemento = (Element) listaCasas.item(i);

        if (casaElemento.hasAttribute("numero")) {
            String numero = casaElemento.getAttribute("numero");

            if (numero.equals(casaBuscada)) {
                // CAMBIO AQUÍ: Buscamos la etiqueta <propietario>
                NodeList propietarios = casaElemento.getElementsByTagName("propietario");

                if (propietarios.getLength() > 0) {
                    Element prop = (Element) propietarios.item(0);
                    // CAMBIO AQUÍ: Dentro de propietario, buscamos <nombre>
                    NodeList nombres = prop.getElementsByTagName("nombre");
                    
                    if (nombres.getLength() > 0) {
                        String nombre = nombres.item(0).getTextContent().trim();
                        if (!nombre.isEmpty()) {
                            return nombre;
                        }
                    }
                }
            }
        }
    }
    return "Sin propietario";
}
    
    
    
    private boolean existePagoMesActual(Document doc, String casaBuscada, String mesActual, String anioActual) {
    NodeList listaPagos = doc.getElementsByTagName("pago");

    for (int i = 0; i < listaPagos.getLength(); i++) {
        Element pago = (Element) listaPagos.item(i);

        String casa = obtenerTexto(pago, "casa");
        String mes = obtenerTexto(pago, "mes");
        String anio = obtenerTexto(pago, "año");

        if (casa.equals(casaBuscada) && mes.equals(mesActual) && anio.equals(anioActual)) {
            return true;
        }
    }

    return false;
}
    
    private double calcularTotalPagadoAnio(Document doc, String casaBuscada, String anioActual) {
    double total = 0;

    NodeList listaPagos = doc.getElementsByTagName("pago");

    for (int i = 0; i < listaPagos.getLength(); i++) {
        Element pago = (Element) listaPagos.item(i);

        String casa = obtenerTexto(pago, "casa");
        String anio = obtenerTexto(pago, "año");
        String cuotaTexto = obtenerTexto(pago, "cuota");

        if (casa.equals(casaBuscada) && anio.equals(anioActual)) {
            try {
                total += Double.parseDouble(cuotaTexto);
            } catch (NumberFormatException e) {
                System.out.println("Error convirtiendo cuota: " + cuotaTexto);
            }
        }
    }

    return total;
}
    
    private String obtenerTexto(Element elementoPadre, String etiqueta) {
    NodeList lista = elementoPadre.getElementsByTagName(etiqueta);

    if (lista.getLength() > 0) {
        return lista.item(0).getTextContent().trim();
    }

    return "";
}
    
    private String obtenerNombreMes(int numeroMes) {
    switch (numeroMes) {
        case 1:
            return "Enero";
        case 2:
            return "Febrero";
        case 3:
            return "Marzo";
        case 4:
            return "Abril";
        case 5:
            return "Mayo";
        case 6:
            return "Junio";
        case 7:
            return "Julio";
        case 8:
            return "Agosto";
        case 9:
            return "Septiembre";
        case 10:
            return "Octubre";
        case 11:
            return "Noviembre";
        case 12:
            return "Diciembre";
        default:
            return "";
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReporteGeneral = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        lblMesActual = new javax.swing.JLabel();
        btnVolvermenu = new javax.swing.JButton();
        lblResumenMes = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblReporteGeneral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblReporteGeneral);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitulo.setText("Reporte General de Residencial Vista Verde");

        lblMesActual.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblMesActual.setText("Mes Actual: Mayo 2026");

        btnVolvermenu.setBackground(new java.awt.Color(0, 0, 51));
        btnVolvermenu.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        btnVolvermenu.setText("MENU");
        btnVolvermenu.addActionListener(this::btnVolvermenuActionPerformed);

        lblResumenMes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblResumenMes.setText("Recaudado este mes: Q15,000.00 / Esperado: Q45,000.00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(287, 287, 287)
                .addComponent(lblMesActual)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(btnVolvermenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblResumenMes)
                .addGap(115, 115, 115))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(61, 61, 61))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMesActual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolvermenu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblResumenMes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolvermenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolvermenuActionPerformed
        MenuPrincipal menu = new MenuPrincipal ();
        menu.setVisible(true);
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnVolvermenuActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> new ReporteGeneral().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVolvermenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMesActual;
    private javax.swing.JLabel lblResumenMes;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblReporteGeneral;
    // End of variables declaration//GEN-END:variables
}

    package com.mycompany.condominio_vistaverde;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import java.awt.Dimension;

public class CondominioVistaVerde {

    public static void main(String[] args) {

        // 1. CONFIGURACIONES GLOBALES DE UI (Hacer esto ANTES de cargar el tema)
        
        // Redondeo de bordes para un diseño más moderno
        UIManager.put("Button.arc", 15); // Botones más redondeados
        UIManager.put("Component.arc", 10); // Cajas de texto y combos redondeados
        UIManager.put("TextComponent.arc", 10);

        // Optimizaciones específicas para Tablas y Reportes (Mejora la lectura de datos)
        UIManager.put("Table.rowHeight", 30); // Filas más altas para que los datos no se vean apretados
        UIManager.put("Table.showHorizontalLines", true); // Mostrar divisiones horizontales
        UIManager.put("Table.showVerticalLines", true); // Mostrar divisiones verticales
        UIManager.put("Table.intercellSpacing", new Dimension(1, 1)); // Espaciado entre celdas
        UIManager.put("Table.selectionBackground", new java.awt.Color(63, 81, 181)); // Azul profesional al seleccionar fila
        
        // Mejoras en el Scroll (Barras de desplazamiento más limpias)
        UIManager.put("ScrollBar.showButtons", false);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new java.awt.Insets(2, 2, 2, 2));

        // 2. ACTIVAR EL TEMA
        try {
            /* * NOTA: Si usas una versión reciente de FlatLaf (3.0 o superior), 
             * puedes intentar usar FlatMacDarkLaf.setup() importando:
             * import com.formdev.flatlaf.themes.FlatMacDarkLaf;
             * Da un aspecto estilo macOS espectacular.
             */
            FlatDarkLaf.setup();
        } catch (Exception e) {
            System.err.println("Error al cargar FlatLaf: " + e.getMessage());
        }

        // 3. MOSTRAR FORMULARIO LOGIN
        java.awt.EventQueue.invokeLater(() -> {
            new LoginPrincipal().setVisible(true);
        });
    }
}
package com.mycompany.condominio_vistaverde;

import com.formdev.flatlaf.FlatDarkLaf;

public class CondominioVistaVerde {

    public static void main(String[] args) {

        // Activar FlatLaf
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            System.out.println("Error al cargar FlatLaf");
        }

        // Mostrar formulario Login
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
package com.mycompany.condominio_vistaverde;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {
    private Image imagen;
    private final String rutaImagen;

    public PanelFondo(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            // Carga la imagen desde los recursos del proyecto
            imagen = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
            // Dibuja la imagen adaptándose al tamaño de la ventana
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        } catch (Exception e) {
            System.out.println("No se encontró la imagen en: " + rutaImagen);
        }
    }
}
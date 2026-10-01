package com.aerolineavs.tarifas;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicación de escritorio Swing.
 */
public final class AppSwing {

    private AppSwing() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaTarifas ventana = new VentanaTarifas(new PricingServiceImpl());
            ventana.setVisible(true);
        });
    }
}
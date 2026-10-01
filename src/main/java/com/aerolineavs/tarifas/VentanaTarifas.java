package com.aerolineavs.tarifas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

/**
 * Ventana Swing para solicitar el perfil del viajero y mostrar su tarifa.
 */
public final class VentanaTarifas extends JFrame {

    private static final Color COLOR_FONDO = new Color(246, 248, 247);
    private static final Color COLOR_PRIMARIO = new Color(20, 76, 68);

    private final IPricingService pricingService;
    private final JSpinner edadSpinner = new JSpinner(new SpinnerNumberModel(30, 0, 120, 1));
    private final JSpinner vuelosSpinner = new JSpinner(new SpinnerNumberModel(6, 0, 100, 1));
    private final JSpinner ingresosSpinner = new JSpinner(
            new SpinnerNumberModel(25000.0, 0.0, 10000000.0, 1000.0)
    );
    private final JComboBox<TipoViajero> tipoViajeroCombo = new JComboBox<>(TipoViajero.values());
    private final JComboBox<ClaseVuelo> claseVueloCombo = new JComboBox<>(ClaseVuelo.values());
    private final JComboBox<RegionDestino> destinoCombo = new JComboBox<>(RegionDestino.values());
    private final JCheckBox ninosCheck = new JCheckBox("Viaja con niños menores de 12");
    private final JCheckBox padresCheck = new JCheckBox("Vive con sus padres");
    private final JLabel tarifaValor = new JLabel("Completa el perfil y calcula la tarifa");
    private final JLabel descuentoValor = new JLabel(" ");
    private final JTextArea suposicionesValor = new JTextArea(2, 40);

    public VentanaTarifas(IPricingService pricingService) {
        super("AerolineaVS | Tarifas");
        this.pricingService = Objects.requireNonNull(pricingService, "pricingService");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(720, 650));
        setSize(780, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(crearCabecera(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);

        tipoViajeroCombo.setSelectedItem(TipoViajero.GENERAL);
        claseVueloCombo.setSelectedItem(ClaseVuelo.TURISTA);
        destinoCombo.setSelectedItem(RegionDestino.EUROPA);
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(COLOR_PRIMARIO);
        cabecera.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        JLabel titulo = new JLabel("Calculadora de tarifas");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));

        JLabel subtitulo = new JLabel("Encuentra la tarifa que corresponde a tu perfil de viaje");
        subtitulo.setForeground(new Color(222, 238, 233));
        subtitulo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 4));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);
        cabecera.add(textos, BorderLayout.CENTER);
        return cabecera;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout(0, 18));
        contenido.setBackground(COLOR_FONDO);
        contenido.setBorder(BorderFactory.createEmptyBorder(22, 28, 24, 28));

        JPanel campos = new JPanel(new GridLayout(0, 2, 14, 12));
        campos.setOpaque(false);
        campos.add(crearCampo("Edad", edadSpinner));
        campos.add(crearCampo("Vuelos al año", vuelosSpinner));
        campos.add(crearCampo("Ingresos anuales (€)", ingresosSpinner));
        campos.add(crearCampo("Tipo de viajero", tipoViajeroCombo));
        campos.add(crearCampo("Clase preferida", claseVueloCombo));
        campos.add(crearCampo("Destino preferido", destinoCombo));
        campos.add(crearCampo("Viaje con niños", ninosCheck));
        campos.add(crearCampo("Situación familiar", padresCheck));

        JButton calcularButton = new JButton("Calcular tarifa");
        calcularButton.setBackground(COLOR_PRIMARIO);
        calcularButton.setForeground(Color.WHITE);
        calcularButton.setFocusPainted(false);
        calcularButton.setPreferredSize(new Dimension(170, 42));
        calcularButton.addActionListener(event -> calcularTarifa());

        JPanel accion = new JPanel(new BorderLayout());
        accion.setOpaque(false);
        accion.add(calcularButton, BorderLayout.EAST);

        JPanel resultado = crearPanelResultado();
        JPanel seccionInferior = new JPanel(new BorderLayout(0, 14));
        seccionInferior.setOpaque(false);
        seccionInferior.add(accion, BorderLayout.NORTH);
        seccionInferior.add(resultado, BorderLayout.CENTER);

        contenido.add(campos, BorderLayout.NORTH);
        contenido.add(seccionInferior, BorderLayout.CENTER);
        return contenido;
    }

    private JPanel crearCampo(String etiqueta, java.awt.Component control) {
        JPanel campo = new JPanel(new BorderLayout(0, 6));
        campo.setOpaque(false);

        JLabel titulo = new JLabel(etiqueta);
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        titulo.setForeground(new Color(48, 60, 56));
        campo.add(titulo, BorderLayout.NORTH);
        campo.add(control, BorderLayout.CENTER);
        return campo;
    }

    private JPanel crearPanelResultado() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(213, 222, 218)),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));

        JLabel titulo = new JLabel("Resultado");
        titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titulo.setForeground(COLOR_PRIMARIO);

        tarifaValor.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));
        tarifaValor.setForeground(new Color(35, 49, 44));
        descuentoValor.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        descuentoValor.setForeground(COLOR_PRIMARIO);

        suposicionesValor.setEditable(false);
        suposicionesValor.setLineWrap(true);
        suposicionesValor.setWrapStyleWord(true);
        suposicionesValor.setOpaque(false);
        suposicionesValor.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        suposicionesValor.setForeground(new Color(88, 99, 94));

        JPanel valores = new JPanel(new GridLayout(0, 1, 0, 7));
        valores.setOpaque(false);
        valores.add(tarifaValor);
        valores.add(descuentoValor);
        valores.add(suposicionesValor);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(valores, BorderLayout.CENTER);
        return panel;
    }

    private void calcularTarifa() {
        ClientePotencial cliente = new ClientePotencial(
                ((Number) edadSpinner.getValue()).intValue(),
                ((Number) vuelosSpinner.getValue()).intValue(),
                (TipoViajero) tipoViajeroCombo.getSelectedItem(),
                (ClaseVuelo) claseVueloCombo.getSelectedItem(),
                (RegionDestino) destinoCombo.getSelectedItem(),
                ((Number) ingresosSpinner.getValue()).doubleValue(),
                ninosCheck.isSelected(),
                padresCheck.isSelected()
        );

        try {
            ResultadoTarifa resultado = pricingService.calcular(cliente);
            tarifaValor.setText(resultado.tarifa().getNombre());
            descuentoValor.setText("Descuento: " + resultado.tarifa().getDescuentoPorcentaje() + "%");
            suposicionesValor.setText(resultado.suposiciones());
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    exception.getMessage(),
                    "No se pudo calcular la tarifa",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
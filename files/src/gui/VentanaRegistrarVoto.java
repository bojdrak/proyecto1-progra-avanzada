package gui;

import javax.swing.*;
import java.awt.*;
import sistema.SistemaConsultas;
import sistema.Votante;
import sistema.ConsultaCiudadana;

public class VentanaRegistrarVoto extends JFrame {
    private SistemaConsultas sistema;
    private JComboBox<String> comboVotantes;
    private JComboBox<String> comboConsultas;
    private JPanel panelVotacion;

    public VentanaRegistrarVoto(SistemaConsultas sistema) {
        this.sistema = sistema;
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Registrar Voto");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Panel de selección
        JPanel panelSeleccion = new JPanel(new GridLayout(2, 2, 10, 10));

        panelSeleccion.add(new JLabel("Seleccionar Votante:"));
        comboVotantes = new JComboBox<>();
        actualizarComboVotantes();
        panelSeleccion.add(comboVotantes);

        panelSeleccion.add(new JLabel("Seleccionar Consulta:"));
        comboConsultas = new JComboBox<>();
        actualizarComboConsultas();
        panelSeleccion.add(comboConsultas);

        JButton btnComenzar = new JButton("Comenzar Votación");
        btnComenzar.addActionListener(e -> comenzarVotacion());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelSeleccion, BorderLayout.CENTER);
        panelSuperior.add(btnComenzar, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel de votación
        panelVotacion = new JPanel();
        panelVotacion.setLayout(new BoxLayout(panelVotacion, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelVotacion);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void actualizarComboVotantes() {
        comboVotantes.removeAllItems();
        for (Votante votante : sistema.getVotantes()) {
            comboVotantes.addItem(votante.getNombre() + " - " + votante.getRut());
        }
    }

    private void actualizarComboConsultas() {
        comboConsultas.removeAllItems();
        for (ConsultaCiudadana consulta : sistema.getConsultas()) {
            comboConsultas.addItem(consulta.getNombre());
        }
    }

    private void comenzarVotacion() {
        JOptionPane.showMessageDialog(this, "Funcionalidad de votación en desarrollo");
    }
}

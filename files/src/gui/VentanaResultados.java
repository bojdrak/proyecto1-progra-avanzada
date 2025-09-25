package gui;

import javax.swing.*;
import java.awt.*;
import sistema.SistemaConsultas;

public class VentanaResultados extends JFrame {
    private SistemaConsultas sistema;
    private JTextArea areaResultados;

    public VentanaResultados(SistemaConsultas sistema) {
        this.sistema = sistema;
        configurarVentana();
        crearComponentes();
        mostrarResultados();
    }

    private void configurarVentana() {
        setTitle("Resultados de Consultas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(areaResultados);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> dispose());

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void mostrarResultados() {
        // Usar el método existente del sistema adaptado para Swing
        areaResultados.setText("Resultados de las consultas:\n\n");

        // Aquí iría la lógica para mostrar resultados
        areaResultados.append("Funcionalidad en desarrollo...\n");
    }
}

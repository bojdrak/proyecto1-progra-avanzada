package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import sistema.SistemaConsultas;
import sistema.Votante;

public class VentanaFiltrarVotantes extends JFrame {
    private SistemaConsultas sistema;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;

    public VentanaFiltrarVotantes(SistemaConsultas sistema) {
        this.sistema = sistema;
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Filtrar Votantes");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        JPanel panelFiltros = new JPanel(new GridLayout(3, 2, 10, 10));

        JSpinner spinnerEdadMin = new JSpinner(new SpinnerNumberModel(18, 18, 100, 1));
        JSpinner spinnerEdadMax = new JSpinner(new SpinnerNumberModel(100, 18, 100, 1));
        JTextField txtComuna = new JTextField();

        panelFiltros.add(new JLabel("Edad Minima:"));
        panelFiltros.add(spinnerEdadMin);
        panelFiltros.add(new JLabel("Edad Maxima:"));
        panelFiltros.add(spinnerEdadMax);
        panelFiltros.add(new JLabel("Comuna (opcional):"));
        panelFiltros.add(txtComuna);

        JButton btnFiltrar = new JButton("Filtrar");
        JButton btnVolver = new JButton("Volver");

        btnFiltrar.addActionListener(e -> {
            int edadMin = (Integer) spinnerEdadMin.getValue();
            int edadMax = (Integer) spinnerEdadMax.getValue();
            String comuna = txtComuna.getText();
            filtrarVotantes(edadMin, edadMax, comuna);
        });

        btnVolver.addActionListener(e -> dispose());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFiltros, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnFiltrar);
        panelBotones.add(btnVolver);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        String[] columnas = {"RUT", "Nombre", "Edad", "Direccion"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void filtrarVotantes(int edadMin, int edadMax, String comuna) {
        if (edadMin > edadMax) {
            JOptionPane.showMessageDialog(this, "La edad minima no puede ser mayor que la maxima");
            return;
        }

        java.util.List<Votante> votantesFiltrados = sistema.filtrarVotantes(edadMin, edadMax, comuna);
        modeloTabla.setRowCount(0);

        for (Votante votante : votantesFiltrados) {
            Object[] fila = {votante.getRut(), votante.getNombre(), votante.getEdad(), votante.getDireccion()};
            modeloTabla.addRow(fila);
        }

        JOptionPane.showMessageDialog(this, "Se encontraron " + votantesFiltrados.size() + " votantes que cumplen los criterios");
    }
}
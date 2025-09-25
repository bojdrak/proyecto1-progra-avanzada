package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Map;  // ← AGREGAR ESTE IMPORT
import java.util.HashMap;  // ← AGREGAR ESTE IMPORT
import sistema.SistemaConsultas;

public class VentanaPrincipal extends JFrame {
    private SistemaConsultas sistema;

    public VentanaPrincipal() {
        this.sistema = new SistemaConsultas();
        configurarVentana();
        crearMenuPrincipal();
    }

    private void configurarVentana() {
        setTitle("Sistema de Consultas Ciudadanas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                guardarYSalir();
            }
        });

        JPanel panelBienvenida = new JPanel();
        panelBienvenida.setBackground(new Color(240, 240, 240));
        JLabel lblTitulo = new JLabel("SISTEMA DE CONSULTAS CIUDADANAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0, 100, 200));
        panelBienvenida.add(lblTitulo);
        add(panelBienvenida, BorderLayout.NORTH);
    }

    private void crearMenuPrincipal() {
        JPanel panelBotones = new JPanel(new GridLayout(7, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton btnGestionVotantes = crearBoton("Gestion de Votantes", new Color(70, 130, 180));
        JButton btnGestionConsultas = crearBoton("Gestion de Consultas", new Color(60, 179, 113));
        JButton btnRegistrarVoto = crearBoton("Registrar Voto", new Color(205, 133, 63));
        JButton btnMostrarResultados = crearBoton("Mostrar Resultados", new Color(186, 85, 211));
        JButton btnEstadisticas = crearBoton("Estadisticas", new Color(106, 90, 205));
        JButton btnFiltrarVotantes = crearBoton("Filtrar Votantes", new Color(60, 179, 113));
        JButton btnSalir = crearBoton("Salir", new Color(220, 20, 60));

        btnGestionVotantes.addActionListener(e -> new VentanaGestionVotantes(sistema).setVisible(true));
        btnGestionConsultas.addActionListener(e -> new VentanaGestionConsultas(sistema).setVisible(true));
        btnRegistrarVoto.addActionListener(e -> new VentanaRegistrarVoto(sistema).setVisible(true));
        btnMostrarResultados.addActionListener(e -> new VentanaResultados(sistema).setVisible(true));
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
        btnFiltrarVotantes.addActionListener(e -> new VentanaFiltrarVotantes(sistema).setVisible(true));
        btnSalir.addActionListener(e -> guardarYSalir());

        panelBotones.add(btnGestionVotantes);
        panelBotones.add(btnGestionConsultas);
        panelBotones.add(btnRegistrarVoto);
        panelBotones.add(btnMostrarResultados);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnFiltrarVotantes);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);
    }

    private void guardarYSalir() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Desea guardar los datos antes de salir?", "Confirmar Salida",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            sistema.guardarDatos();
            System.exit(0);
        } else if (opcion == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    private void mostrarEstadisticas() {
        Map<String, Integer> estadisticas = sistema.obtenerEstadisticasEdad();
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADISTICAS DE VOTANTES POR EDAD ===\n\n");
        for (Map.Entry<String, Integer> entry : estadisticas.entrySet()) {
            sb.append("Rango ").append(entry.getKey()).append(": ")
                    .append(entry.getValue()).append(" votantes\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Estadisticas", JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }
}
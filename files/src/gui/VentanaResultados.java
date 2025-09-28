package gui;

import javax.swing.*;
import java.awt.*;
import sistema.SistemaConsultas;
import java.util.List;
import java.util.Map;

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
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADOS DE CONSULTAS ===\n\n");

        if (sistema.getResultados().isEmpty()) {
            sb.append("No hay votos registrados aún.\n");
        } else {
            for (Map.Entry<String, Map<String, Map<String, List<String>>>> consultaEntry : sistema.getResultados().entrySet()) {
                sb.append("CONSULTA: ").append(consultaEntry.getKey()).append("\n");
                sb.append("=".repeat(50)).append("\n");

                for (Map.Entry<String, Map<String, List<String>>> temaEntry : consultaEntry.getValue().entrySet()) {
                    sb.append("TEMA: ").append(temaEntry.getKey()).append("\n");
                    sb.append("-".repeat(30)).append("\n");

                    for (Map.Entry<String, List<String>> preguntaEntry : temaEntry.getValue().entrySet()) {
                        sb.append("Pregunta: ").append(preguntaEntry.getKey()).append("\n");

                        // Contar votos por respuesta
                        Map<String, Integer> conteo = new java.util.HashMap<>();
                        for (String respuesta : preguntaEntry.getValue()) {
                            conteo.put(respuesta, conteo.getOrDefault(respuesta, 0) + 1);
                        }

                        // Mostrar resultados
                        for (Map.Entry<String, Integer> resultado : conteo.entrySet()) {
                            sb.append("  ").append(resultado.getKey())
                                    .append(": ").append(resultado.getValue())
                                    .append(" votos\n");
                        }
                        sb.append("\n");
                    }
                }
                sb.append("\n");
            }
        }

        // Mostrar estadísticas generales
        sb.append("=== ESTADÍSTICAS GENERALES ===\n");
        sb.append("Total de votantes registrados: ").append(sistema.getVotantes().size()).append("\n");
        sb.append("Total de consultas: ").append(sistema.getConsultas().size()).append("\n");

        // Cálculo compatible con Java 11
        int totalVotos = 0;
        for (Map<String, Map<String, List<String>>> temaMap : sistema.getResultados().values()) {
            for (Map<String, List<String>> preguntaMap : temaMap.values()) {
                for (List<String> respuestas : preguntaMap.values()) {
                    totalVotos += respuestas.size();
                }
            }
        }
        sb.append("Total de votos registrados: ").append(totalVotos).append("\n");

        areaResultados.setText(sb.toString());
    }
}
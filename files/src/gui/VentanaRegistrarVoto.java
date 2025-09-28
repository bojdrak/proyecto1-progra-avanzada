package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import sistema.SistemaConsultas;
import sistema.Votante;
import sistema.ConsultaCiudadana;
import sistema.Tema;
import sistema.Pregunta;

public class VentanaRegistrarVoto extends JFrame {
    private SistemaConsultas sistema;
    private JComboBox<String> comboVotantes;
    private JComboBox<String> comboConsultas;
    private JPanel panelVotacion;
    private JButton btnRegistrarVoto;
    private JLabel lblEstado;

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
        comboVotantes = new JComboBox<>();
        comboConsultas = new JComboBox<>();
        lblEstado = new JLabel("Seleccione un votante");
        lblEstado.setForeground(Color.BLUE);

        JPanel panelSeleccion = new JPanel(new GridLayout(3, 2, 10, 10));

        panelSeleccion.add(new JLabel("Seleccionar Votante:"));
        panelSeleccion.add(comboVotantes);

        panelSeleccion.add(new JLabel("Seleccionar Consulta:"));
        panelSeleccion.add(comboConsultas);

        panelSeleccion.add(new JLabel("Estado:"));
        panelSeleccion.add(lblEstado);

        JButton btnComenzar = new JButton("Comenzar Votación");
        btnComenzar.addActionListener(e -> comenzarVotacion());

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelSeleccion, BorderLayout.CENTER);
        panelSuperior.add(btnComenzar, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        panelVotacion = new JPanel();
        panelVotacion.setLayout(new BoxLayout(panelVotacion, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelVotacion);
        add(scrollPane, BorderLayout.CENTER);


        btnRegistrarVoto = new JButton("Registrar Voto");
        btnRegistrarVoto.setEnabled(false);
        btnRegistrarVoto.addActionListener(e -> registrarVoto());

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnRegistrarVoto);
        add(panelInferior, BorderLayout.SOUTH);

        actualizarComboVotantes();

        comboVotantes.addActionListener(e -> {
            actualizarComboConsultas();
            actualizarEstado();
        });
        comboConsultas.addActionListener(e -> actualizarEstado());
    }

    private void actualizarEstado() {
        // Caso 1: sin votante
        if (comboVotantes.getSelectedItem() == null) {
            lblEstado.setText("Seleccione un votante");
            lblEstado.setForeground(Color.BLUE);
            comboConsultas.setEnabled(false);
            btnRegistrarVoto.setEnabled(false);
            return;
        }

        // Caso 2: sin consultas cargadas
        if (comboConsultas.getItemCount() == 0) {
            lblEstado.setText("No hay consultas disponibles para votar");
            lblEstado.setForeground(Color.RED);
            comboConsultas.setEnabled(false);
            btnRegistrarVoto.setEnabled(false);
            return;
        }

        comboConsultas.setEnabled(true);

        String votanteInfo = (String) comboVotantes.getSelectedItem();
        String rutVotante = votanteInfo.split(" - ")[1];

        Object sel = comboConsultas.getSelectedItem();
        if (sel == null) {
            // Hay consultas, pero no hay selección activa (puede pasar al repoblar)
            lblEstado.setText("Puede votar - Seleccione una consulta");
            lblEstado.setForeground(Color.BLUE);
            btnRegistrarVoto.setEnabled(false);
            return;
        }

        String consultaNombre = sel.toString().trim();

        if (sistema.rutYaVotoEnConsulta(rutVotante, consultaNombre)) {
            lblEstado.setText("YA VOTÓ EN ESTA CONSULTA - No puede votar nuevamente");
            lblEstado.setForeground(Color.RED);
            btnRegistrarVoto.setEnabled(false);
        } else {
            lblEstado.setText("Puede votar en esta consulta");
            lblEstado.setForeground(Color.GREEN);
            // Habilitaremos el botón solo cuando haya preguntas renderizadas (tras comenzar)
            btnRegistrarVoto.setEnabled(panelVotacion.getComponentCount() > 0);
        }
    }

    private void actualizarComboVotantes() {
        comboVotantes.removeAllItems();
        for (Votante votante : sistema.getVotantes()) {
            comboVotantes.addItem(votante.getNombre() + " - " + votante.getRut());
        }


        if (comboVotantes.getItemCount() > 0) {
            comboVotantes.setSelectedIndex(0);
            actualizarComboConsultas();
        } else {
            lblEstado.setText("No hay votantes registrados");
            lblEstado.setForeground(Color.RED);
        }
    }

    private void actualizarComboConsultas() {
        comboConsultas.removeAllItems();

        if (comboVotantes.getSelectedItem() == null) {
            lblEstado.setText("Seleccione un votante");
            lblEstado.setForeground(Color.BLUE);
            comboConsultas.setEnabled(false);
            btnRegistrarVoto.setEnabled(false);
            return;
        }

        String votanteInfo = (String) comboVotantes.getSelectedItem();
        String rutVotante = votanteInfo.split(" - ")[1];
        List<ConsultaCiudadana> consultasDisponibles = sistema.getConsultasDisponiblesParaVotante(rutVotante);

        for (ConsultaCiudadana consulta : consultasDisponibles) {
            comboConsultas.addItem(consulta.getNombre());
        }

        if (comboConsultas.getItemCount() > 0) {
            comboConsultas.setSelectedIndex(0);
            comboConsultas.setEnabled(true);
            // Asegura que el estado se calcule con la selección ya aplicada
            SwingUtilities.invokeLater(this::actualizarEstado);
        } else {
            comboConsultas.setEnabled(false);
            lblEstado.setText("No hay consultas disponibles para votar");
            lblEstado.setForeground(Color.RED);
            btnRegistrarVoto.setEnabled(false);
        }
    }


    private void comenzarVotacion() {
        if (comboVotantes.getSelectedItem() == null || comboConsultas.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un votante y una consulta");
            return;
        }

        String votanteInfo = (String) comboVotantes.getSelectedItem();
        String rutVotante = votanteInfo.split(" - ")[1];
        String consultaNombre = ((String) comboConsultas.getSelectedItem()).trim();

        if (sistema.rutYaVotoEnConsulta(rutVotante, consultaNombre)) {
            JOptionPane.showMessageDialog(this,
                    "Error: El RUT " + rutVotante + " ya ha votado en la consulta seleccionada.\nNo puede votar dos veces en la misma.",
                    "Votación No Permitida", JOptionPane.ERROR_MESSAGE);
            return;
        }


        ConsultaCiudadana consulta = null;
        for (ConsultaCiudadana c : sistema.getConsultas()) {
            if (c.getNombre().equals(consultaNombre)) {
                consulta = c;
                break;
            }
        }

        if (consulta == null || consulta.getTemas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La consulta seleccionada no tiene temas configurados");
            return;
        }

        panelVotacion.removeAll();
        panelVotacion.setLayout(new BoxLayout(panelVotacion, BoxLayout.Y_AXIS));

        // Crear componentes para cada pregunta
        for (Tema tema : consulta.getTemas()) {
            JLabel lblTema = new JLabel("Tema: " + tema.getNombre());
            lblTema.setFont(new Font("Arial", Font.BOLD, 14));
            panelVotacion.add(lblTema);
            panelVotacion.add(Box.createVerticalStrut(5));

            for (Pregunta pregunta : tema.getPreguntas()) {
                JPanel panelPregunta = new JPanel(new BorderLayout());
                panelPregunta.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(), "Pregunta"));

                JLabel lblPregunta = new JLabel(pregunta.getTexto());
                panelPregunta.add(lblPregunta, BorderLayout.NORTH);

                JPanel panelRespuestas = new JPanel(new FlowLayout());
                String tipoRespuesta = pregunta.getTipoRespuesta();

                if (tipoRespuesta.contains("/")) {
                    String[] opciones = tipoRespuesta.split("/");
                    ButtonGroup grupo = new ButtonGroup();
                    for (String opcion : opciones) {
                        JRadioButton radio = new JRadioButton(opcion.trim());
                        grupo.add(radio);
                        panelRespuestas.add(radio);
                    }
                } else {
                    JTextField txtRespuesta = new JTextField(20);
                    panelRespuestas.add(txtRespuesta);
                }

                panelPregunta.add(panelRespuestas, BorderLayout.CENTER);
                panelPregunta.putClientProperty("consulta", consulta.getNombre());
                panelPregunta.putClientProperty("tema", tema.getNombre());
                panelPregunta.putClientProperty("pregunta", pregunta.getTexto());

                panelVotacion.add(panelPregunta);
                panelVotacion.add(Box.createVerticalStrut(10));
            }
        }

        panelVotacion.revalidate();
        panelVotacion.repaint();
        btnRegistrarVoto.setEnabled(true);
        actualizarEstado();
    }

    private void registrarVoto() {
        if (comboVotantes.getSelectedItem() == null || comboConsultas.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un votante y una consulta");
            return;
        }

        String votanteInfo = (String) comboVotantes.getSelectedItem();
        String rutVotante = votanteInfo.split(" - ")[1];
        String consultaNombre = ((String) comboConsultas.getSelectedItem()).trim();

        // (Opcional) puedes confiar en la validación del backend y quitar este prechequeo
        if (sistema.rutYaVotoEnConsulta(rutVotante, consultaNombre)) {
            JOptionPane.showMessageDialog(this,
                    "Error: Este RUT ya votó en la consulta seleccionada. No puede votar dos veces en la misma.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, String> respuestas = new HashMap<>();
        boolean todasRespondidas = true;
        StringBuilder preguntasSinResponder = new StringBuilder();

        // Recolectar respuestas
        for (Component comp : panelVotacion.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panelPregunta = (JPanel) comp;
                String consulta = (String) panelPregunta.getClientProperty("consulta");
                String tema = (String) panelPregunta.getClientProperty("tema");
                String pregunta = (String) panelPregunta.getClientProperty("pregunta");

                if (consulta != null && tema != null && pregunta != null) {
                    String clave = consulta + "|" + tema + "|" + pregunta;
                    String respuesta = obtenerRespuestaDelPanel(panelPregunta);

                    if (respuesta.isEmpty()) {
                        todasRespondidas = false;
                        preguntasSinResponder.append("- ").append(pregunta).append("\n");
                    } else {
                        respuestas.put(clave, respuesta);
                    }
                }
            }
        }

        if (!todasRespondidas) {
            JOptionPane.showMessageDialog(this,
                    "Debe responder todas las preguntas:\n\n" + preguntasSinResponder.toString(),
                    "Preguntas Sin Responder", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Registrar voto en el sistema (el backend valida duplicados por consulta)
            sistema.registrarVoto(rutVotante, respuestas);

            JOptionPane.showMessageDialog(this,
                    "Voto registrado exitosamente para: " + rutVotante + "\n\n¡Gracias por participar!",
                    "Voto Registrado", JOptionPane.INFORMATION_MESSAGE);

            // 1) Limpiar panel de preguntas
            btnRegistrarVoto.setEnabled(false);
            panelVotacion.removeAll();
            panelVotacion.revalidate();
            panelVotacion.repaint();

            // 2) Repoblar consultas disponibles para el mismo votante
            actualizarComboConsultas();

            // 3) Recalcular el estado DESPUÉS de que el combo haya sido actualizado
            //    (invokeLater asegura que la selección del combo ya esté aplicada)
            SwingUtilities.invokeLater(this::actualizarEstado);

            // 4) Por defecto, deshabilitar registrar hasta comenzar nueva votación
            btnRegistrarVoto.setEnabled(false);

        } catch (IllegalStateException | IllegalArgumentException ex) {
            // Errores de dominio del backend (ej.: voto duplicado en misma consulta)
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar el voto: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private String obtenerRespuestaDelPanel(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panelRespuestas = (JPanel) comp;
                for (Component compResp : panelRespuestas.getComponents()) {
                    if (compResp instanceof JRadioButton) {
                        JRadioButton radio = (JRadioButton) compResp;
                        if (radio.isSelected()) {
                            return radio.getText();
                        }
                    } else if (compResp instanceof JTextField) {
                        JTextField txt = (JTextField) compResp;
                        return txt.getText().trim();
                    }
                }
            }
        }
        return "";
    }
}
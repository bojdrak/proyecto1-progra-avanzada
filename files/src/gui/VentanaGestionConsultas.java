package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import sistema.SistemaConsultas;
import sistema.ConsultaCiudadana;
import sistema.Tema;
import sistema.Pregunta;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class VentanaGestionConsultas extends JFrame {
    private SistemaConsultas sistema;
    private JTree arbolConsultas;
    private DefaultTreeModel modeloArbol;

    public VentanaGestionConsultas(SistemaConsultas sistema) {
        this.sistema = sistema;
        configurarVentana();
        crearComponentes();
        actualizarArbol();
    }

    private void configurarVentana() {
        setTitle("Gestion de Consultas y Temas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        JPanel panelSuperior = new JPanel(new FlowLayout());

        JButton btnAgregarConsulta = new JButton("Agregar Consulta");
        JButton btnAgregarTema = new JButton("Agregar Tema");
        JButton btnAgregarPregunta = new JButton("Agregar Pregunta");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver");

        btnAgregarConsulta.addActionListener(e -> agregarConsulta());
        btnAgregarTema.addActionListener(e -> agregarTema());
        btnAgregarPregunta.addActionListener(e -> agregarPregunta());
        btnEditar.addActionListener(e -> editarElemento());
        btnEliminar.addActionListener(e -> eliminarElemento());
        btnVolver.addActionListener(e -> dispose());

        panelSuperior.add(btnAgregarConsulta);
        panelSuperior.add(btnAgregarTema);
        panelSuperior.add(btnAgregarPregunta);
        panelSuperior.add(btnEditar);
        panelSuperior.add(btnEliminar);
        panelSuperior.add(btnVolver);

        add(panelSuperior, BorderLayout.NORTH);

        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Consultas Ciudadanas");
        modeloArbol = new DefaultTreeModel(raiz);
        arbolConsultas = new JTree(modeloArbol);
        JScrollPane scrollPane = new JScrollPane(arbolConsultas);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void actualizarArbol() {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Consultas Ciudadanas");

        for (ConsultaCiudadana consulta : sistema.getConsultas()) {
            DefaultMutableTreeNode nodoConsulta = new DefaultMutableTreeNode(consulta);

            for (Tema tema : consulta.getTemas()) {
                DefaultMutableTreeNode nodoTema = new DefaultMutableTreeNode(tema);

                for (Pregunta pregunta : tema.getPreguntas()) {
                    DefaultMutableTreeNode nodoPregunta = new DefaultMutableTreeNode(pregunta);
                    nodoTema.add(nodoPregunta);
                }
                nodoConsulta.add(nodoTema);
            }
            raiz.add(nodoConsulta);
        }

        modeloArbol.setRoot(raiz);
        modeloArbol.reload();
    }

    private void agregarConsulta() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField txtNombre = new JTextField();
        JTextField txtFecha = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        JTextField txtDescripcion = new JTextField();

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panel.add(txtFecha);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);

        int resultado = JOptionPane.showConfirmDialog(this, panel,
                "Agregar Consulta", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                LocalDate fecha = LocalDate.parse(txtFecha.getText());
                ConsultaCiudadana consulta = new ConsultaCiudadana(
                        txtNombre.getText(), fecha, txtDescripcion.getText());
                sistema.agregarConsulta(consulta);
                actualizarArbol();
                JOptionPane.showMessageDialog(this, "Consulta agregada exitosamente");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Error: Formato de fecha inválido. Use YYYY-MM-DD",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void agregarTema() {
        if (sistema.getConsultas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay consultas disponibles. Agregue una consulta primero.");
            return;
        }

        // Seleccionar consulta - Compatible con Java 11
        String[] opcionesConsultas = new String[sistema.getConsultas().size()];
        for (int i = 0; i < sistema.getConsultas().size(); i++) {
            opcionesConsultas[i] = sistema.getConsultas().get(i).getNombre();
        }

        String consultaSeleccionada = (String) JOptionPane.showInputDialog(this,
                "Seleccione la consulta:", "Seleccionar Consulta",
                JOptionPane.QUESTION_MESSAGE, null, opcionesConsultas, opcionesConsultas[0]);

        if (consultaSeleccionada == null) return;

        ConsultaCiudadana consulta = null;
        for (ConsultaCiudadana c : sistema.getConsultas()) {
            if (c.getNombre().equals(consultaSeleccionada)) {
                consulta = c;
                break;
            }
        }

        if (consulta == null) return;

        // Datos del tema
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField txtNombre = new JTextField();
        JTextField txtDescripcion = new JTextField();

        panel.add(new JLabel("Nombre del Tema:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);

        int resultado = JOptionPane.showConfirmDialog(this, panel,
                "Agregar Tema a: " + consulta.getNombre(), JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            Tema tema = new Tema(txtNombre.getText(), txtDescripcion.getText());
            consulta.agregarTema(tema);
            actualizarArbol();
            JOptionPane.showMessageDialog(this, "Tema agregado exitosamente");
        }
    }

    private void agregarPregunta() {
        if (sistema.getConsultas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay consultas disponibles.");
            return;
        }

        // Encontrar todas las consultas con temas - Compatible con Java 11
        List<ConsultaCiudadana> consultasConTemas = new ArrayList<>();
        for (ConsultaCiudadana consulta : sistema.getConsultas()) {
            if (!consulta.getTemas().isEmpty()) {
                consultasConTemas.add(consulta);
            }
        }

        if (consultasConTemas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay temas disponibles. Agregue un tema primero.");
            return;
        }

        // Seleccionar consulta y tema
        JPanel panelSeleccion = new JPanel(new GridLayout(2, 2, 10, 10));

        JComboBox<String> comboConsultas = new JComboBox<>();
        JComboBox<String> comboTemas = new JComboBox<>();

        for (ConsultaCiudadana c : consultasConTemas) {
            comboConsultas.addItem(c.getNombre());
        }

        // Actualizar temas cuando cambia la consulta
        comboConsultas.addActionListener(e -> {
            comboTemas.removeAllItems();
            String consultaNombre = (String) comboConsultas.getSelectedItem();
            for (ConsultaCiudadana c : consultasConTemas) {
                if (c.getNombre().equals(consultaNombre)) {
                    for (Tema t : c.getTemas()) {
                        comboTemas.addItem(t.getNombre());
                    }
                    break;
                }
            }
        });

        // Cargar temas iniciales
        if (comboConsultas.getItemCount() > 0) {
            comboConsultas.setSelectedIndex(0);
        }

        panelSeleccion.add(new JLabel("Consulta:"));
        panelSeleccion.add(comboConsultas);
        panelSeleccion.add(new JLabel("Tema:"));
        panelSeleccion.add(comboTemas);

        int seleccion = JOptionPane.showConfirmDialog(this, panelSeleccion,
                "Seleccionar Consulta y Tema", JOptionPane.OK_CANCEL_OPTION);

        if (seleccion != JOptionPane.OK_OPTION) return;

        // Datos de la pregunta
        JPanel panelPregunta = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField txtTexto = new JTextField();
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{
                "Si/No", "Alta/Media/Baja", "Plazas/Parques/Ciclovias",
                "Infraestructura/Recursos/Personal", "Texto Libre"
        });

        panelPregunta.add(new JLabel("Texto de la pregunta:"));
        panelPregunta.add(txtTexto);
        panelPregunta.add(new JLabel("Tipo de respuesta:"));
        panelPregunta.add(comboTipo);

        int resultado = JOptionPane.showConfirmDialog(this, panelPregunta,
                "Agregar Pregunta", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            String consultaNombre = (String) comboConsultas.getSelectedItem();
            String temaNombre = (String) comboTemas.getSelectedItem();

            ConsultaCiudadana consulta = null;
            for (ConsultaCiudadana c : consultasConTemas) {
                if (c.getNombre().equals(consultaNombre)) {
                    consulta = c;
                    break;
                }
            }

            if (consulta != null) {
                Tema tema = null;
                for (Tema t : consulta.getTemas()) {
                    if (t.getNombre().equals(temaNombre)) {
                        tema = t;
                        break;
                    }
                }

                if (tema != null) {
                    Pregunta pregunta = new Pregunta(txtTexto.getText(),
                            (String) comboTipo.getSelectedItem());
                    tema.agregarPregunta(pregunta);
                    actualizarArbol();
                    JOptionPane.showMessageDialog(this, "Pregunta agregada exitosamente");
                }
            }
        }
    }

    private void editarElemento() {
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) arbolConsultas.getLastSelectedPathComponent();
        if (nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para editar");
            return;
        }

        int nivel = nodoSeleccionado.getLevel();

        if (nivel == 1) {
            editarConsulta(nodoSeleccionado);
        } else if (nivel == 2) {
            editarTema(nodoSeleccionado);
        } else if (nivel == 3) {
            editarPregunta(nodoSeleccionado);
        }
    }

    private void eliminarElemento() {
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) arbolConsultas.getLastSelectedPathComponent();
        if (nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para eliminar");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este elemento?", "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            int nivel = nodoSeleccionado.getLevel();

            if (nivel == 1) {
                eliminarConsulta(nodoSeleccionado);
            } else if (nivel == 2) {
                eliminarTema(nodoSeleccionado);
            } else if (nivel == 3) {
                eliminarPregunta(nodoSeleccionado);
            }
        }
    }

    private void editarConsulta(DefaultMutableTreeNode nodoConsulta) {
        ConsultaCiudadana consulta = (ConsultaCiudadana) nodoConsulta.getUserObject();

        JTextField txtNombre = new JTextField(consulta.getNombre());
        JTextField txtFecha = new JTextField(consulta.getFecha().format(DateTimeFormatter.ISO_LOCAL_DATE));
        JTextField txtDescripcion = new JTextField(consulta.getDescripcion());

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panel.add(txtFecha);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Consulta", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                consulta.setNombre(txtNombre.getText());
                consulta.setFecha(LocalDate.parse(txtFecha.getText()));
                consulta.setDescripcion(txtDescripcion.getText());
                actualizarArbol();
                JOptionPane.showMessageDialog(this, "Consulta editada exitosamente");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Error: Formato de fecha inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarTema(DefaultMutableTreeNode nodoTema) {
        Tema tema = (Tema) nodoTema.getUserObject();
        DefaultMutableTreeNode nodoPadre = (DefaultMutableTreeNode) nodoTema.getParent();
        ConsultaCiudadana consulta = (ConsultaCiudadana) nodoPadre.getUserObject();

        JTextField txtNombre = new JTextField(tema.getNombre());
        JTextField txtDescripcion = new JTextField(tema.getDescripcion());

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Tema", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            tema.setNombre(txtNombre.getText());
            tema.setDescripcion(txtDescripcion.getText());
            actualizarArbol();
            JOptionPane.showMessageDialog(this, "Tema editado exitosamente");
        }
    }

    private void editarPregunta(DefaultMutableTreeNode nodoPregunta) {
        Pregunta pregunta = (Pregunta) nodoPregunta.getUserObject();

        JTextField txtTexto = new JTextField(pregunta.getTexto());
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{
                "Si/No", "Alta/Media/Baja", "Plazas/Parques/Ciclovias",
                "Infraestructura/Recursos/Personal", "Texto Libre"
        });
        comboTipo.setSelectedItem(pregunta.getTipoRespuesta());

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Texto:"));
        panel.add(txtTexto);
        panel.add(new JLabel("Tipo respuesta:"));
        panel.add(comboTipo);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Pregunta", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            pregunta.setTexto(txtTexto.getText());
            pregunta.setTipoRespuesta((String) comboTipo.getSelectedItem());
            actualizarArbol();
            JOptionPane.showMessageDialog(this, "Pregunta editada exitosamente");
        }
    }

    private void eliminarConsulta(DefaultMutableTreeNode nodoConsulta) {
        ConsultaCiudadana consulta = (ConsultaCiudadana) nodoConsulta.getUserObject();
        sistema.getConsultas().remove(consulta);
        actualizarArbol();
        JOptionPane.showMessageDialog(this, "Consulta eliminada exitosamente");
    }

    private void eliminarTema(DefaultMutableTreeNode nodoTema) {
        Tema tema = (Tema) nodoTema.getUserObject();
        DefaultMutableTreeNode nodoPadre = (DefaultMutableTreeNode) nodoTema.getParent();
        ConsultaCiudadana consulta = (ConsultaCiudadana) nodoPadre.getUserObject();

        consulta.getTemas().remove(tema);
        actualizarArbol();
        JOptionPane.showMessageDialog(this, "Tema eliminado exitosamente");
    }

    private void eliminarPregunta(DefaultMutableTreeNode nodoPregunta) {
        Pregunta pregunta = (Pregunta) nodoPregunta.getUserObject();
        DefaultMutableTreeNode nodoPadre = (DefaultMutableTreeNode) nodoPregunta.getParent();
        Tema tema = (Tema) nodoPadre.getUserObject();

        tema.getPreguntas().remove(pregunta);
        actualizarArbol();
        JOptionPane.showMessageDialog(this, "Pregunta eliminada exitosamente");
    }
}
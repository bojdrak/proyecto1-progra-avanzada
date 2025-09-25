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
            DefaultMutableTreeNode nodoConsulta = new DefaultMutableTreeNode(consulta.getNombre());

            for (Tema tema : consulta.getTemas()) {
                DefaultMutableTreeNode nodoTema = new DefaultMutableTreeNode(tema.getNombre());

                for (Pregunta pregunta : tema.getPreguntas()) {
                    DefaultMutableTreeNode nodoPregunta = new DefaultMutableTreeNode(pregunta.getTexto());
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
        JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo");
    }

    private void agregarTema() {
        JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo");
    }

    private void agregarPregunta() {
        JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo");
    }

    private void editarElemento() {
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) arbolConsultas.getLastSelectedPathComponent();
        if (nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para editar");
            return;
        }

        if (nodoSeleccionado.getLevel() == 1) {
            editarConsulta(nodoSeleccionado);
        }
    }

    private void eliminarElemento() {
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) arbolConsultas.getLastSelectedPathComponent();
        if (nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un elemento para eliminar");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Esta seguro de eliminar este elemento?", "Confirmar Eliminacion",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (nodoSeleccionado.getLevel() == 1) {
                eliminarConsulta(nodoSeleccionado);
            }
        }
    }

    private void editarConsulta(DefaultMutableTreeNode nodoConsulta) {
        String nombreActual = nodoConsulta.getUserObject().toString();
        int indiceConsulta = encontrarIndiceConsulta(nombreActual);

        if (indiceConsulta != -1) {
            ConsultaCiudadana consulta = sistema.getConsultas().get(indiceConsulta);

            JTextField txtNombre = new JTextField(consulta.getNombre());
            JTextField txtDescripcion = new JTextField(consulta.getDescripcion());

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Nombre:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Descripcion:"));
            panel.add(txtDescripcion);

            int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Consulta", JOptionPane.OK_CANCEL_OPTION);
            if (resultado == JOptionPane.OK_OPTION) {
                consulta.setNombre(txtNombre.getText());
                consulta.setDescripcion(txtDescripcion.getText());
                actualizarArbol();
                JOptionPane.showMessageDialog(this, "Consulta editada exitosamente");
            }
        }
    }

    private void eliminarConsulta(DefaultMutableTreeNode nodoConsulta) {
        String nombre = nodoConsulta.getUserObject().toString();
        int indice = encontrarIndiceConsulta(nombre);

        if (indice != -1 && sistema.eliminarConsulta(indice)) {
            actualizarArbol();
            JOptionPane.showMessageDialog(this, "Consulta eliminada exitosamente");
        }
    }

    private int encontrarIndiceConsulta(String nombre) {
        for (int i = 0; i < sistema.getConsultas().size(); i++) {
            if (sistema.getConsultas().get(i).getNombre().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }
}
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import sistema.SistemaConsultas;
import sistema.Votante;
import exceptions.EdadInvalidaException;
import exceptions.VotanteYaExisteException;

public class VentanaGestionVotantes extends JFrame {
    private SistemaConsultas sistema;
    private JTable tablaVotantes;
    private DefaultTableModel modeloTabla;

    public VentanaGestionVotantes(SistemaConsultas sistema) {
        this.sistema = sistema;
        configurarVentana();
        crearComponentes();
        actualizarTabla();
    }

    private void configurarVentana() {
        setTitle("Gestion de Votantes");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Panel superior con botones
        JPanel panelSuperior = new JPanel(new FlowLayout());

        JButton btnAgregar = new JButton("Agregar Votante");
        JButton btnEditar = new JButton("Editar Votante");
        JButton btnEliminar = new JButton("Eliminar Votante");
        JButton btnVolver = new JButton("Volver al Menu");

        btnAgregar.addActionListener(e -> agregarVotante());
        btnEditar.addActionListener(e -> editarVotante());
        btnEliminar.addActionListener(e -> eliminarVotante());
        btnVolver.addActionListener(e -> dispose());

        panelSuperior.add(btnAgregar);
        panelSuperior.add(btnEditar);
        panelSuperior.add(btnEliminar);
        panelSuperior.add(btnVolver);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de votantes
        String[] columnas = {"RUT", "Nombre", "Edad", "Direccion"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaVotantes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaVotantes);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Votante votante : sistema.getVotantes()) {
            Object[] fila = {
                    votante.getRut(),
                    votante.getNombre(),
                    votante.getEdad(),
                    votante.getDireccion()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void agregarVotante() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField txtRut = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtEdad = new JTextField();
        JTextField txtDireccion = new JTextField();

        panel.add(new JLabel("RUT:"));
        panel.add(txtRut);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Edad:"));
        panel.add(txtEdad);
        panel.add(new JLabel("Direccion:"));
        panel.add(txtDireccion);

        int resultado = JOptionPane.showConfirmDialog(this, panel,
                "Agregar Votante", JOptionPane.OK_CANCEL_OPTION);
        // aqui se manejan las excepciones SIA 2.9
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                Votante votante = new Votante(
                        txtRut.getText(),
                        txtNombre.getText(),
                        Integer.parseInt(txtEdad.getText()),
                        txtDireccion.getText()
                );
                sistema.agregarVotante(votante);
                actualizarTabla();
            
                JOptionPane.showMessageDialog(this, "Votante agregado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: La edad debe ser un numero",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (EdadInvalidaException | VotanteYaExisteException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarVotante() {
        int filaSeleccionada = tablaVotantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un votante para editar");
            return;
        }

        Votante votante = sistema.getVotantes().get(filaSeleccionada);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField txtRut = new JTextField(votante.getRut());
        JTextField txtNombre = new JTextField(votante.getNombre());
        JTextField txtEdad = new JTextField(String.valueOf(votante.getEdad()));
        JTextField txtDireccion = new JTextField(votante.getDireccion());

        panel.add(new JLabel("RUT:"));
        panel.add(txtRut);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Edad:"));
        panel.add(txtEdad);
        panel.add(new JLabel("Direccion:"));
        panel.add(txtDireccion);

        int resultado = JOptionPane.showConfirmDialog(this, panel,
                "Editar Votante", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            votante.setRut(txtRut.getText());
            votante.setNombre(txtNombre.getText());
            votante.setEdad(Integer.parseInt(txtEdad.getText()));
            votante.setDireccion(txtDireccion.getText());
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Votante editado exitosamente");
        }
    }

    private void eliminarVotante() {
        int filaSeleccionada = tablaVotantes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un votante para eliminar");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "Esta seguro de eliminar este votante?", "Confirmar Eliminacion",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            sistema.getVotantes().remove(filaSeleccionada);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Votante eliminado exitosamente");
        }
    }
}
package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import sistema.SistemaConsultas;
import sistema.Votante;
import sistema.ConsultaCiudadana;
import sistema.Tema;
import sistema.Pregunta;


public class VentanaBuscarElemento extends JFrame {
    private final SistemaConsultas sistema;
    private final JTextField txtQuery;
    private final JComboBox<String> cboAlcance;
    private final DefaultListModel<String> modeloResultados;
    private final JList<String> listaResultados;

    public VentanaBuscarElemento(SistemaConsultas sistema) {
        super("Buscar (Votantes / Consultas / Temas / Preguntas)");
        this.sistema = sistema;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel barra = new JPanel(new BorderLayout(10, 10));
        txtQuery = new JTextField();
        cboAlcance = new JComboBox<>(new String[] {
            "Todo",
            "Votantes",
            "Consultas",
            "Temas",
            "Preguntas"
        });
        JButton btnBuscar = new JButton("Buscar");

        barra.add(new JLabel("Texto a buscar:"), BorderLayout.WEST);
        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.add(txtQuery, BorderLayout.CENTER);
        centro.add(cboAlcance, BorderLayout.EAST);
        barra.add(centro, BorderLayout.CENTER);
        barra.add(btnBuscar, BorderLayout.EAST);

        add(barra, BorderLayout.NORTH);

        modeloResultados = new DefaultListModel<>();
        listaResultados = new JList<>(modeloResultados);
        add(new JScrollPane(listaResultados), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> ejecutarBusqueda());
        getRootPane().setDefaultButton(btnBuscar);
    }

    private void ejecutarBusqueda() {
        String q = txtQuery.getText().trim().toLowerCase();
        String alcance = (String) cboAlcance.getSelectedItem();

        modeloResultados.clear();
        List<String> resultados = new ArrayList<>();

        boolean buscarVot = alcance.equals("Todo") || alcance.equals("Votantes");
        boolean buscarCon = alcance.equals("Todo") || alcance.equals("Consultas");
        boolean buscarTem = alcance.equals("Todo") || alcance.equals("Temas");
        boolean buscarPre = alcance.equals("Todo") || alcance.equals("Preguntas");

        if (buscarVot) {
            for (Votante v : sistema.getVotantes()) {
                if (q.isEmpty()
                    || v.getRut().toLowerCase().contains(q)
                    || v.getNombre().toLowerCase().contains(q)
                    || v.getDireccion().toLowerCase().contains(q)
                    || String.valueOf(v.getEdad()).contains(q)) {
                    resultados.add("VOTANTE ▶ Rut=" + v.getRut() + " | Nombre=" + v.getNombre()
                            + " | Edad=" + v.getEdad() + " | Dirección=" + v.getDireccion());
                }
            }
        }

        if (buscarCon || buscarTem || buscarPre) {
            for (ConsultaCiudadana c : sistema.getConsultas()) {
                boolean matchConsulta = q.isEmpty()
                        || c.getNombre().toLowerCase().contains(q)
                        || c.getDescripcion().toLowerCase().contains(q)
                        || c.getFecha().toString().contains(q);

                if (buscarCon && matchConsulta) {
                    resultados.add("CONSULTA ▶ " + c.getNombre() + " (" + c.getFecha() + ") — " + c.getDescripcion());
                }

                for (Tema t : c.getTemas()) {
                    boolean matchTema = q.isEmpty()
                            || t.getNombre().toLowerCase().contains(q)
                            || t.getDescripcion().toLowerCase().contains(q);

                    if (buscarTem && (matchTema || matchConsulta)) {
                        resultados.add("TEMA ▶ " + c.getNombre() + " ▸ " + t.getNombre() + " — " + t.getDescripcion());
                    }

                    if (buscarPre) {
                        for (Pregunta p : t.getPreguntas()) {
                            boolean matchPreg = q.isEmpty()
                                    || p.getTexto().toLowerCase().contains(q)
                                    || p.getTipoRespuesta().toLowerCase().contains(q);
                            if (matchPreg || matchTema || matchConsulta) {
                                resultados.add("PREGUNTA ▶ " + c.getNombre() + " ▸ " + t.getNombre()
                                        + " ▸ " + p.getTexto() + " (" + p.getTipoRespuesta() + ")");
                            }
                        }
                    }
                }
            }
        }

        if (resultados.isEmpty()) {
            modeloResultados.addElement("Sin resultados para: \"" + q + "\" en " + alcance + ".");
        } else {
            resultados.forEach(modeloResultados::addElement);
        }
    }
}


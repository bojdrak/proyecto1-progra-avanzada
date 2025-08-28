import java.util.*;

public class SistemaConsultas {
    private List<Votante> votantes;
    private List<ConsultaCiudadana> consultas;
    private Map<String, Map<String, Map<String, List<String>>>> resultados;

    public SistemaConsultas() {
        this.votantes = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.resultados = new HashMap<>();
    }

    // Métodos para votantes
    public void agregarVotante(Votante votante) {
        votantes.add(votante);
    }

    public List<Votante> getVotantes() {
        return votantes;
    }

    public void mostrarVotantes() {
        System.out.println("\n=== LISTA DE VOTANTES ===");
        if (votantes.isEmpty()) {
            System.out.println("No hay votantes registrados.");
            return;
        }
        for (int i = 0; i < votantes.size(); i++) {
            System.out.println((i + 1) + ". " + votantes.get(i));
        }
    }

    // Métodos para consultas
    public void agregarConsulta(ConsultaCiudadana consulta) {
        consultas.add(consulta);
    }

    public List<ConsultaCiudadana> getConsultas() {
        return consultas;
    }

    public void mostrarConsultas() {
        System.out.println("\n=== LISTA DE CONSULTAS ===");
        if (consultas.isEmpty()) {
            System.out.println("No hay consultas registradas.");
            return;
        }
        for (int i = 0; i < consultas.size(); i++) {
            System.out.println((i + 1) + ". " + consultas.get(i));
        }
    }

    // Método para registrar votos (completo)
    public void registrarVoto(String rutVotante, Map<String, String> respuestas) {
        for (Map.Entry<String, String> entry : respuestas.entrySet()) {
            String[] partes = entry.getKey().split("\\|");
            String consulta = partes[0];
            String tema = partes[1];
            String pregunta = partes[2];
            String respuesta = entry.getValue();

            resultados.putIfAbsent(consulta, new HashMap<>());
            resultados.get(consulta).putIfAbsent(tema, new HashMap<>());
            resultados.get(consulta).get(tema).putIfAbsent(pregunta, new ArrayList<>());
            resultados.get(consulta).get(tema).get(pregunta).add(respuesta);
        }
    }

    // Método para mostrar resultados (completo)
    public void mostrarResultados() {
        System.out.println("\n=== RESULTADOS AGREGADOS ===");

        if (resultados.isEmpty()) {
            System.out.println("No hay votos registrados aún.");
            return;
        }

        for (Map.Entry<String, Map<String, Map<String, List<String>>>> consultaEntry : resultados.entrySet()) {
            System.out.println("\nCONSULTA: " + consultaEntry.getKey());

            for (Map.Entry<String, Map<String, List<String>>> temaEntry : consultaEntry.getValue().entrySet()) {
                System.out.println("\n  TEMA: " + temaEntry.getKey());

                for (Map.Entry<String, List<String>> preguntaEntry : temaEntry.getValue().entrySet()) {
                    System.out.println("    PREGUNTA: " + preguntaEntry.getKey());

                    Map<String, Integer> conteo = new HashMap<>();
                    for (String respuesta : preguntaEntry.getValue()) {
                        conteo.put(respuesta, conteo.getOrDefault(respuesta, 0) + 1);
                    }

                    for (Map.Entry<String, Integer> resultado : conteo.entrySet()) {
                        System.out.println("      " + resultado.getKey() + ": " + resultado.getValue() + " votos");
                    }
                }
            }
        }
    }
}
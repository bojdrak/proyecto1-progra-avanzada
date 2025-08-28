import java.util.*;

public class SistemaConsultas {
    private List<Votante> votantes;
    private List<ConsultaCiudadana> consultas;
    private Map<String, Map<String, Map<String, List<String>>>> resultados;

    public SistemaConsultas() {
        votantes = new ArrayList<>();
        consultas = new ArrayList<>();
        resultados = new HashMap<>();
    }

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

    public void agregarConsulta(ConsultaCiudadana consulta) {
        consultas.add(consulta);
    }
    public List<ConsultaCiudadana> getConsultas(){
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

    public void registrarVoto(String rutVotante, Map<String, String> respuestas) {
        System.out.println("Voto registrado para: " + rutVotante);
    }
    public void mostrarResultados() {
        System.out.println("Funcionalidad de mostrar resultados no implementada aún.");
    }
}
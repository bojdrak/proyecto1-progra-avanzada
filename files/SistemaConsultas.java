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

    public void agregarVotante(Votante votante) {}
    public List<Votante> getVotantes() { return votantes; }
    public void mostrarVotantes() {}

    public void agregarConsulta(ConsultaCiudadana consulta) {}
    public List<ConsultaCiudadana> getConsultas() { return consultas; }
    public void mostrarConsultas() {}

    public void registrarVoto(String rutVotante, Map<String, String> respuestas) {}
    public void mostrarResultados() {}
}
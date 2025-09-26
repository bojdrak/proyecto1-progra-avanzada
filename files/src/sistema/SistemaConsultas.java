package sistema;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

import exceptions.EdadInvalidaException;
import exceptions.VotanteYaExisteException;

public class SistemaConsultas {
    private List<Votante> votantes;
    private List<ConsultaCiudadana> consultas;
    private Map<String, Map<String, Map<String, List<String>>>> resultados;

    public SistemaConsultas() {
        this.votantes = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.resultados = new HashMap<>();

        try {
            PersistenciaDatos.cargarDatos(this);
        } catch (IOException | ClassNotFoundException | EdadInvalidaException | VotanteYaExisteException e) {
            System.out.println("[Sistema] Error al cargar datos: " + e.getMessage());
        }
    }

    public void guardarDatos() {
        PersistenciaDatos.guardarDatos(this);
    }

    // SIA2.5: Filtrar votantes
    public List<Votante> filtrarVotantes(int edadMinima, int edadMaxima, String comuna) {
        List<Votante> votantesFiltrados = new ArrayList<>();
        for (Votante votante : votantes) {
            boolean cumpleEdad = votante.getEdad() >= edadMinima && votante.getEdad() <= edadMaxima;
            boolean cumpleComuna = comuna.isEmpty() || votante.getDireccion().toLowerCase().contains(comuna.toLowerCase());
            if (cumpleEdad && cumpleComuna) {
                votantesFiltrados.add(votante);
            }
        }
        return votantesFiltrados;
    }

    // SIA2.5: Estadisticas
    public Map<String, Integer> obtenerEstadisticasEdad() {
        Map<String, Integer> estadisticas = new HashMap<>();
        estadisticas.put("18-25", 0);
        estadisticas.put("26-40", 0);
        estadisticas.put("41-60", 0);
        estadisticas.put("61+", 0);

        for (Votante votante : votantes) {
            int edad = votante.getEdad();
            if (edad >= 18 && edad <= 25) {
                estadisticas.put("18-25", estadisticas.get("18-25") + 1);
            } else if (edad <= 40) {
                estadisticas.put("26-40", estadisticas.get("26-40") + 1);
            } else if (edad <= 60) {
                estadisticas.put("41-60", estadisticas.get("41-60") + 1);
            } else if (edad > 60) {
                estadisticas.put("61+", estadisticas.get("61+") + 1);
            }
        }
        return estadisticas;
    }

    // SIA2.4: Eliminar consulta
    public boolean eliminarConsulta(int indice) {
        if (indice >= 0 && indice < consultas.size()) {
            consultas.remove(indice);
            return true;
        }
        return false;
    }

    // SIA2.4: Eliminar tema
    public boolean eliminarTema(int indiceConsulta, int indiceTema) {
        if (indiceConsulta >= 0 && indiceConsulta < consultas.size()) {
            ConsultaCiudadana consulta = consultas.get(indiceConsulta);
            if (indiceTema >= 0 && indiceTema < consulta.getTemas().size()) {
                consulta.getTemas().remove(indiceTema);
                return true;
            }
        }
        return false;
    }

    // SIA2.3: Manejo de excepciones
    public void agregarVotante(Votante votante) throws VotanteYaExisteException, EdadInvalidaException {
        if (votante.getEdad() < 18) {
            throw new EdadInvalidaException("El votante debe ser mayor de edad.");
        }
        for (Votante existente : votantes) {
            if (existente.getRut().equals(votante.getRut())) {
                throw new VotanteYaExisteException("El votante con RUT " + votante.getRut() + " ya existe.");
            }
        }
        votantes.add(votante); 
    }
    public void agregarConsulta(ConsultaCiudadana consulta) { consultas.add(consulta); }

    public List<Votante> getVotantes() { return votantes; }
    public List<ConsultaCiudadana> getConsultas() { return consultas; }
    public Map<String, Map<String, Map<String, List<String>>>> getResultados() { return resultados; }

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

    public void mostrarResultados() {
        System.out.println("\n=== RESULTADOS AGREGADOS ===");
        if (resultados.isEmpty()) {
            System.out.println("No hay votos registrados aun.");
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
    
    // SIA2.5: Generar reporte CSV
    public void generarReporteVotantes(String nombreArchivo){
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("RUT,Nombre,Edad,Direccion\n");
            //filas de votantes
           for (Votante votante : votantes) {
               writer.write(votante.getRut() + "," + votante.getNombre() + "," + votante.getEdad() + "," + votante.getDireccion() + "\n");
            }
            writer.flush();
            System.out.println("Reporte de votantes generado: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte de votantes: " + e.getMessage());
        }
    }
}


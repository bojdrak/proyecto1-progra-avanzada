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
    private Map<String, Set<String>> votacionesPorConsulta; // Control de votación por consulta

    public SistemaConsultas() {
        this.votantes = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.resultados = new HashMap<>();
        this.votacionesPorConsulta = new HashMap<>(); // Inicializar el control por consulta

        try {
            PersistenciaDatos.cargarDatos(this);
        } catch (Exception e) {
            System.out.println("[Sistema] Error al cargar datos: " + e.getMessage());
        }
    }

    public void guardarDatos() {
        PersistenciaDatos.guardarDatos(this);
    }

    public boolean rutYaVotoEnConsulta(String rut, String nombreConsulta) {
        if (!votacionesPorConsulta.containsKey(nombreConsulta)) {
            return false; // Nadie ha votado en esta consulta aún
        }
        return votacionesPorConsulta.get(nombreConsulta).contains(rut);
    }

    // Método para obtener consultas disponibles para un RUT (donde no haya votado)
    public List<ConsultaCiudadana> getConsultasDisponiblesParaVotante(String rut) {
        List<ConsultaCiudadana> consultasDisponibles = new ArrayList<>();

        for (ConsultaCiudadana consulta : consultas) {
            // Solo agregar consultas donde este RUT NO haya votado
            if (!rutYaVotoEnConsulta(rut, consulta.getNombre())) {
                consultasDisponibles.add(consulta);
            }
        }
        return consultasDisponibles;
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

    // SIA2.9 y 2.8: Manejo de excepciones
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

    public void agregarConsulta(ConsultaCiudadana consulta) {
        consultas.add(consulta);
    }

    public List<Votante> getVotantes() { return votantes; }
    public List<ConsultaCiudadana> getConsultas() { return consultas; }
    public Map<String, Map<String, Map<String, List<String>>>> getResultados() { return resultados; }

    // Método para verificar si un RUT ya votó en ALGUNA consulta
    public boolean rutYaVoto(String rut) {
        // Un RUT ha votado si ha votado en ALGUNA consulta
        for (Set<String> ruts : votacionesPorConsulta.values()) {
            if (ruts.contains(rut)) {
                return true;
            }
        }
        return false;
    }

    // Método registrarVoto con control de votación por consulta
    public void registrarVoto(String rutVotante, Map<String, String> respuestas) {
        if (respuestas.isEmpty()) {
            throw new IllegalArgumentException("No hay respuestas para registrar");
        }

        // Obtener el nombre de la consulta desde la primera respuesta
        String primeraClave = respuestas.keySet().iterator().next();
        String nombreConsulta = primeraClave.split("\\|")[0];

        // Validar que el RUT no haya votado en ESTA consulta específica
        if (rutYaVotoEnConsulta(rutVotante, nombreConsulta)) {
            throw new IllegalStateException("El RUT " + rutVotante + " ya ha votado en la consulta: " + nombreConsulta);
        }

        // Validar que el RUT exista en el sistema
        boolean rutExiste = false;
        for (Votante votante : votantes) {
            if (votante.getRut().equals(rutVotante)) {
                rutExiste = true;
                break;
            }
        }

        if (!rutExiste) {
            throw new IllegalArgumentException("El RUT " + rutVotante + " no está registrado en el sistema.");
        }

        // Registrar las respuestas
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

        // Marcar el RUT como que ya votó en ESTA consulta específica
        votacionesPorConsulta.putIfAbsent(nombreConsulta, new HashSet<>());
        votacionesPorConsulta.get(nombreConsulta).add(rutVotante);
    }

    // Getters y Setters para el control de votación por consulta
    public Map<String, Set<String>> getVotacionesPorConsulta() {
        return votacionesPorConsulta;
    }

    public void setVotacionesPorConsulta(Map<String, Set<String>> votacionesPorConsulta) {
        this.votacionesPorConsulta = votacionesPorConsulta;
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

    // SIA2.10: Generar reporte CSV
    public void generarReporteVotantes(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("RUT,Nombre,Edad,Direccion\n");
            for (Votante votante : votantes) {
                writer.write(votante.getRut() + "," + votante.getNombre() + "," +
                        votante.getEdad() + "," + votante.getDireccion() + "\n");
            }
            writer.flush();
            System.out.println("Reporte de votantes generado: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte de votantes: " + e.getMessage());
        }
    }

    public void exportarResultados(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("Resultados de Consultas Ciudadanas\n");
            writer.write("==================================\n\n");

            for (Map.Entry<String, Map<String, Map<String, List<String>>>> consultaEntry : resultados.entrySet()) {
                writer.write("Consulta: " + consultaEntry.getKey() + "\n");

                for (Map.Entry<String, Map<String, List<String>>> temaEntry : consultaEntry.getValue().entrySet()) {
                    writer.write("  Tema: " + temaEntry.getKey() + "\n");

                    for (Map.Entry<String, List<String>> preguntaEntry : temaEntry.getValue().entrySet()) {
                        writer.write("    Pregunta: " + preguntaEntry.getKey() + "\n");

                        Map<String, Integer> conteo = new HashMap<>();
                        for (String respuesta : preguntaEntry.getValue()) {
                            conteo.put(respuesta, conteo.getOrDefault(respuesta, 0) + 1);
                        }

                        for (Map.Entry<String, Integer> resultado : conteo.entrySet()) {
                            writer.write("      " + resultado.getKey() + ": " + resultado.getValue() + " votos\n");
                        }
                        writer.write("\n");
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error al exportar resultados: " + e.getMessage());
        }
    }
}
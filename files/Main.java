import java.util.*;
import java.time.LocalDate;

public class Main { // Clase principal que contiene el menu y la logica de interacción con el usuario
    public static void main(String[] args) {
        Main app = new Main(); // Se crea la aplicación principal
        app.mostrarMenuPrincipal(); // Se inicia el menú de interacción
    }

    private Scanner scanner; // Para leer entradas de teclado
    private SistemaConsultas sistema; // Objeto que administra votantes, consultas y votos

    // Constructor: inicializa el scanner y el sistema
    public Main() {
        this.scanner = new Scanner(System.in);
        this.sistema = new SistemaConsultas();
    }
    // ----------- MENU PRINCIPAL -----------
    private void mostrarMenuPrincipal() {
        int opcion;
        do {    
            // Menú de opciones
            System.out.println("\n=== SISTEMA DE CONSULTAS CIUDADANAS ===");
            System.out.println("1. Gestión de Votantes");
            System.out.println("2. Gestión de Consultas y Temas");
            System.out.println("3. Registrar Voto");
            System.out.println("4. Mostrar Resultados");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de linea

            // Se redirige según la opcion elegida
            switch (opcion) {
                case 1:
                    gestionarVotantes();
                    break;
                case 2:
                    gestionarConsultas();
                    break;
                case 3:
                    registrarVoto();
                    break;
                case 4:
                    mostrarResultados();
                    break;
                case 5:
                    System.out.println("¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 5); // Se repite hasta que el usuario elija salir
    }
    // ----------- MENU DE GESTION DE VOTANTES -----------
    private void gestionarVotantes() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE VOTANTES ===");
            System.out.println("1. Agregar votante");
            System.out.println("2. Listar votantes");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarVotante();
                    break;
                case 2:
                    sistema.mostrarVotantes();
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 3);
    }
    // ----------- MENU DE GESTION DE CONSULTAS Y TEMAS -----------
    private void gestionarConsultas() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE CONSULTAS ===");
            System.out.println("1. Agregar consulta");
            System.out.println("2. Agregar tema a consulta");
            System.out.println("3. Agregar pregunta a tema");
            System.out.println("4. Listar consultas");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    agregarConsulta();
                    break;
                case 2:
                    agregarTemaAConsulta();
                    break;
                case 3:
                    agregarPreguntaATema();
                    break;
                case 4:
                    sistema.mostrarConsultas();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 5);
    }
    // ----------- METODOS DE APOYO -----------

    // Agrega un votante pidiendo datos al usuario
    private void agregarVotante() {
        System.out.print("RUT: ");
        String rut = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();

        Votante votante = new Votante(rut, nombre, edad, direccion);
        sistema.agregarVotante(votante);
        System.out.println("Votante agregado exitosamente");
    }
    // Agrega una consulta nueva
    private void agregarConsulta() {
        System.out.print("Nombre de la consulta: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Año: ");
        int ano = scanner.nextInt();
        System.out.print("Mes: ");
        int mes = scanner.nextInt();
        System.out.print("Día: ");
        int dia = scanner.nextInt();
        scanner.nextLine();

        ConsultaCiudadana consulta = new ConsultaCiudadana(nombre,
                LocalDate.of(ano, mes, dia), descripcion);
        sistema.agregarConsulta(consulta);
        System.out.println("Consulta agregada exitosamente");
    }
    // Agrega un tema a una consulta seleccionada
    private void agregarTemaAConsulta() {
        sistema.mostrarConsultas();
        if (sistema.getConsultas().isEmpty()) return;

        System.out.print("Seleccione el número de la consulta: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();

        if (index >= 0 && index < sistema.getConsultas().size()) {
            System.out.print("Nombre del tema: ");
            String nombre = scanner.nextLine();
            System.out.print("Descripción del tema: ");
            String descripcion = scanner.nextLine();

            Tema tema = new Tema(nombre, descripcion);
            sistema.getConsultas().get(index).agregarTema(tema);
            System.out.println("Tema agregado exitosamente");
        } else {
            System.out.println("Consulta no válida");
        }
    }
    // Agrega una pregunta a un tema dentro de una consulta
    private void agregarPreguntaATema() {
        sistema.mostrarConsultas();
        if (sistema.getConsultas().isEmpty()) return;

        System.out.print("Seleccione el número de la consulta: ");
        int consultaIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (consultaIndex >= 0 && consultaIndex < sistema.getConsultas().size()) {
            ConsultaCiudadana consulta = sistema.getConsultas().get(consultaIndex);
            consulta.mostrarTemas();

            if (consulta.getTemas().isEmpty()) return;

            System.out.print("Seleccione el número del tema: ");
            int temaIndex = scanner.nextInt() - 1;
            scanner.nextLine();

            if (temaIndex >= 0 && temaIndex < consulta.getTemas().size()) {
                System.out.print("Texto de la pregunta: ");
                String texto = scanner.nextLine();
                System.out.print("Tipo de respuesta: ");
                String tipoRespuesta = scanner.nextLine();

                Pregunta pregunta = new Pregunta(texto, tipoRespuesta);
                consulta.getTemas().get(temaIndex).agregarPregunta(pregunta);
                System.out.println("Pregunta agregada exitosamente");
            } else {
                System.out.println("Tema no válido");
            }
        } else {
            System.out.println("Consulta no válida");
        }
    }
    // Permite registrar un voto de un votante en una consulta
    private void registrarVoto() {
        sistema.mostrarVotantes();
        if (sistema.getVotantes().isEmpty()) return;

        System.out.print("Seleccione el número del votante: ");
        int votanteIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (votanteIndex >= 0 && votanteIndex < sistema.getVotantes().size()) {
            Votante votante = sistema.getVotantes().get(votanteIndex);

            sistema.mostrarConsultas();
            if (sistema.getConsultas().isEmpty()) return;

            System.out.print("Seleccione el número de la consulta: ");
            int consultaIndex = scanner.nextInt() - 1;
            scanner.nextLine();

            if (consultaIndex >= 0 && consultaIndex < sistema.getConsultas().size()) {
                ConsultaCiudadana consulta = sistema.getConsultas().get(consultaIndex);

                System.out.println("Registrando votos para: " + consulta.getNombre());
                Map<String, String> respuestas = new HashMap<>();
                // Itera sobre cada tema y pregunta, solicitando respuestas
                for (Tema tema : consulta.getTemas()) {
                    System.out.println("\nTema: " + tema.getNombre());
                    for (Pregunta pregunta : tema.getPreguntas()) {
                        System.out.println("Pregunta: " + pregunta.getTexto());
                        System.out.print("Respuesta (" + pregunta.getTipoRespuesta() + "): ");
                        String respuesta = scanner.nextLine();
                        // Clave unica: consulta|tema|pregunta
                        String clave = consulta.getNombre() + "|" + tema.getNombre() + "|" + pregunta.getTexto();
                        respuestas.put(clave, respuesta);
                    }
                }
                // Registra el voto en el sistema
                sistema.registrarVoto(votante.getRut(), respuestas);
                System.out.println("Voto registrado exitosamente");
            } else {
                System.out.println("Consulta no válida");
            }
        } else {
            System.out.println("Votante no válido");
        }
    }
    // Muestra los resultados de las votaciones
    private void mostrarResultados() {
        sistema.mostrarResultados();
    }
}
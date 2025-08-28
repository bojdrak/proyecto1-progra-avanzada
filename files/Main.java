import java.util.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Main app = new Main();
        app.cargarDatosIniciales();
        app.mostrarMenuPrincipal();
    }

    private Scanner scanner;
    private SistemaConsultas sistema;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.sistema = new SistemaConsultas();
    }

    private void cargarDatosIniciales() {
        // Agregar algunos votantes de ejemplo
        sistema.agregarVotante(new Votante("12345678-9", "Juan Pérez", 30, "Calle 123"));
        sistema.agregarVotante(new Votante("98765432-1", "María González", 25, "Avenida 456"));

        ConsultaCiudadana consulta1 = new ConsultaCiudadana("Consulta Ambiental",
                LocalDate.of(2024, 6, 15), "Consulta sobre temas medioambientales");

        Tema tema1 = new Tema("Contaminación", "Medidas contra la contaminación");
        tema1.agregarPregunta(new Pregunta("¿Está a favor de prohibir plásticos de un solo uso?", "Si/No"));
        tema1.agregarPregunta(new Pregunta("¿Apoya el uso de energías renovables?", "Si/No"));

        Tema tema2 = new Tema("Transporte", "Mejoras en el transporte público");
        tema2.agregarPregunta(new Pregunta("¿Está de acuerdo con aumentar impuestos a vehículos contaminantes?", "Si/No"));

        consulta1.agregarTema(tema1);
        consulta1.agregarTema(tema2);

        sistema.agregarConsulta(consulta1);
    }

    private void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE CONSULTAS CIUDADANAS ===");
            System.out.println("1. Gestión de Votantes");
            System.out.println("2. Gestión de Consultas y Temas");
            System.out.println("3. Registrar Voto");
            System.out.println("4. Mostrar Resultados");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

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
        } while (opcion != 5);
    }

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

    private void agregarConsulta() {
        System.out.print("Nombre de la consulta: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Año: ");
        int año = scanner.nextInt();
        System.out.print("Mes: ");
        int mes = scanner.nextInt();
        System.out.print("Día: ");
        int dia = scanner.nextInt();
        scanner.nextLine();

        ConsultaCiudadana consulta = new ConsultaCiudadana(nombre,
                LocalDate.of(año, mes, dia), descripcion);
        sistema.agregarConsulta(consulta);
        System.out.println("Consulta agregada exitosamente");
    }

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

                for (Tema tema : consulta.getTemas()) {
                    System.out.println("\nTema: " + tema.getNombre());
                    for (Pregunta pregunta : tema.getPreguntas()) {
                        System.out.println("Pregunta: " + pregunta.getTexto());
                        System.out.print("Respuesta (" + pregunta.getTipoRespuesta() + "): ");
                        String respuesta = scanner.nextLine();
                        String clave = consulta.getNombre() + "|" + tema.getNombre() + "|" + pregunta.getTexto();
                        respuestas.put(clave, respuesta);
                    }
                }

                sistema.registrarVoto(votante.getRut(), respuestas);
                System.out.println("Voto registrado exitosamente");
            } else {
                System.out.println("Consulta no válida");
            }
        } else {
            System.out.println("Votante no válido");
        }
    }

    private void mostrarResultados() {
        sistema.mostrarResultados();
    }
}
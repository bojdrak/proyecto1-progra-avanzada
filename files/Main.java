import java.util.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Main app = new Main();
        app.mostrarMenuPrincipal();
    }

    private Scanner scanner;
    private SistemaConsultas sistema;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.sistema = new SistemaConsultas();
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
        System.out.println("Funcionalidad no implementada en este commit");
    }

    private void agregarConsulta() {
        System.out.println("Funcionalidad no implementada en este commit");
    }

    private void agregarTemaAConsulta() {
        System.out.println("Funcionalidad no implementada en este commit");
    }

    private void agregarPreguntaATema() {
        System.out.println("Funcionalidad no implementada en este commit");
    }

    private void registrarVoto() {
        System.out.println("Funcionalidad no implementada en este commit");
    }

    private void mostrarResultados() {
        System.out.println("Funcionalidad no implementada en este commit");
    }
}
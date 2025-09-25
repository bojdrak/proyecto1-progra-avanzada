package sistema;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class PersistenciaDatos {
    private static final String ARCHIVO_DATOS = "datos_consultas.dat";

    public static void cargarDatos(SistemaConsultas sistema) {
        File archivo = new File(ARCHIVO_DATOS);

        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                @SuppressWarnings("unchecked")
                List<Votante> votantes = (List<Votante>) ois.readObject();
                sistema.getVotantes().clear();
                sistema.getVotantes().addAll(votantes);

                @SuppressWarnings("unchecked")
                List<ConsultaCiudadana> consultas = (List<ConsultaCiudadana>) ois.readObject();
                sistema.getConsultas().clear();
                sistema.getConsultas().addAll(consultas);

                System.out.println("Datos cargados exitosamente desde " + ARCHIVO_DATOS);

            } catch (Exception e) {
                System.out.println("Error cargando datos: " + e.getMessage());
                System.out.println("Creando datos iniciales...");
                crearDatosIniciales(sistema);
            }
        } else {
            System.out.println("Archivo de datos no encontrado. Creando datos iniciales...");
            crearDatosIniciales(sistema);
        }
    }

    public static void guardarDatos(SistemaConsultas sistema) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(sistema.getVotantes());
            oos.writeObject(sistema.getConsultas());
            System.out.println("Datos guardados exitosamente en " + ARCHIVO_DATOS);

        } catch (IOException e) {
            System.out.println("Error guardando datos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void crearDatosIniciales(SistemaConsultas sistema) {
        sistema.agregarVotante(new Votante("11.111.111-1", "Ana Gonzalez", 35, "Av. Principal 123, Santiago"));
        sistema.agregarVotante(new Votante("22.222.222-2", "Carlos Lopez", 42, "Calle Secundaria 456, Providencia"));
        sistema.agregarVotante(new Votante("33.333.333-3", "Maria Torres", 28, "Pasaje Central 789, Las Condes"));
        sistema.agregarVotante(new Votante("44.444.444-4", "Juan Perez", 55, "Av. Forestal 321, Nunoa"));
        sistema.agregarVotante(new Votante("55.555.555-5", "Laura Silva", 31, "Calle Nueva 654, Maipu"));

        ConsultaCiudadana consulta1 = new ConsultaCiudadana("Consulta de Desarrollo Urbano 2024", LocalDate.of(2024, 6, 15), "Consulta sobre mejoras urbanisticas en la comuna");

        Tema temaTransporte = new Tema("Transporte Publico", "Mejoras al sistema de transporte");
        temaTransporte.agregarPregunta("Esta satisfecho con la frecuencia de buses?", "Si/No");
        temaTransporte.agregarPregunta("Que prioridad daria a la expansion del metro?", "Alta/Media/Baja");

        Tema temaAreasVerdes = new Tema("Areas Verdes", "Espacios publicos y recreacion");
        temaAreasVerdes.agregarPregunta("Considera suficientes las areas verdes en su sector?", "Si/No");
        temaAreasVerdes.agregarPregunta("Que tipo de areas verdes prefiere?", "Plazas/Parques/Ciclovias");

        consulta1.agregarTema(temaTransporte);
        consulta1.agregarTema(temaAreasVerdes);
        sistema.agregarConsulta(consulta1);

        ConsultaCiudadana consulta2 = new ConsultaCiudadana("Consulta Educacional 2024", LocalDate.of(2024, 7, 1), "Evaluacion de necesidades educativas en la comuna");

        Tema temaEducacion = new Tema("Infraestructura Escolar", "Condiciones de establecimientos");
        temaEducacion.agregarPregunta("Considera adecuadas las escuelas de su sector?", "Si/No");
        temaEducacion.agregarPregunta("Que mejorarias en los colegios?", "Infraestructura/Recursos/Personal");

        consulta2.agregarTema(temaEducacion);
        sistema.agregarConsulta(consulta2);

        System.out.println("Datos iniciales creados exitosamente");
    }
}
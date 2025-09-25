package sistema;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Tema implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String descripcion;
    private List<Pregunta> preguntas;

    public Tema(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preguntas = new ArrayList<>();
    }

    // Métodos sobrecargados
    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    public void agregarPregunta(String texto, String tipoRespuesta) {
        preguntas.add(new Pregunta(texto, tipoRespuesta));
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Pregunta> getPreguntas() { return preguntas; }
    public void setPreguntas(List<Pregunta> preguntas) { this.preguntas = preguntas; }

    public void mostrarPreguntas() {
        System.out.println("Tema: " + nombre);
        for (int i = 0; i < preguntas.size(); i++) {
            System.out.println((i + 1) + ". " + preguntas.get(i));
        }
    }

    @Override
    public String toString() {
        return "Tema{nombre=" + nombre + ", descripcion=" + descripcion + ", preguntas=" + preguntas.size() + "}";
    }
}
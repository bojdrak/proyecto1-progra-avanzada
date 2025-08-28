import java.util.List;
import java.util.ArrayList;

public class Tema {
    private String nombre;
    private String descripcion;
    private List<Pregunta> preguntas;

    public Tema(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.preguntas = new ArrayList<>();
    }

    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    public void agregarPregunta(String texto, String tipoRespuesta) {
        preguntas.add(new Pregunta(texto, tipoRespuesta));
    }

    public String getNombre() { 
        return nombre;
    }

    public List<Pregunta> getPreguntas() { 
        return preguntas; 
    }

    public void mostrarPreguntas() {
        System.out.println("Tema: " + nombre);
        for (int i = 0; i < preguntas.size(); i++) {
            System.out.println((i + 1) + ". " + preguntas.get(i));
        }
    }
    public String toString() { 
        return "Tema: " + nombre + " (" + preguntas.size() + " preguntas)"; 
        }
}
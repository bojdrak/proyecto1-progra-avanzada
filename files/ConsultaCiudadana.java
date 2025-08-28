import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaCiudadana { // Clase principal que representa una consulta ciudadana
    // Atributos privados de la consulta
    private String nombre; // Nombre de la consulta
    private LocalDate fecha; // Fecha en que se realiza
    private String descripcion; // Breve explicación de la consulta
    private List<Tema> temas; // Lista de temas asociados a la consulta

    public ConsultaCiudadana(String nombre, LocalDate fecha, String descripcion) { // Constructor: inicializa los valores y la lista de temas
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.temas = new ArrayList<>(); // Se crea la lista vacía de temas
    }
    // -------- MÉTODOS SOBRECARGADOS PARA AGREGAR TEMAS --------
    
    // Agrega un tema ya creado (objeto de tipo Tema)
    public void agregarTema(Tema tema) {
        temas.add(tema);
    }
    // Crea un tema nuevo a partir de nombre y descripción, y lo agrega
    public void agregarTema(String nombre, String descripcion) {
        temas.add(new Tema(nombre, descripcion));
    }

    // -------- GETTERS Y SETTERS --------
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Tema> getTemas() { return temas; }
    public void setTemas(List<Tema> temas) { this.temas = temas; }

    // -------- MÉTODOS DE UTILIDAD --------
    public void mostrarTemas() {
        System.out.println("Consulta: " + nombre);
        for (int i = 0; i < temas.size(); i++) {
            System.out.println((i + 1) + ". " + temas.get(i));
        }
    }
    // Representación en texto de la consulta (sobrescribe toString)
    @Override
    public String toString() {
        return "Consulta{" + "nombre=" + nombre + ", fecha=" + fecha +
                ", descripcion=" + descripcion + ", temas=" + temas.size() + "}";
    }
}
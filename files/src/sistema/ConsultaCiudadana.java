package sistema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ConsultaCiudadana implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private LocalDate fecha;
    private String descripcion;
    private List<Tema> temas;

    public ConsultaCiudadana(String nombre, LocalDate fecha, String descripcion) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.temas = new ArrayList<>();
    }

    // Métodos sobrecargados
    public void agregarTema(Tema tema) {
        temas.add(tema);
    }

    public void agregarTema(String nombre, String descripcion) {
        temas.add(new Tema(nombre, descripcion));
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Tema> getTemas() { return temas; }
    public void setTemas(List<Tema> temas) { this.temas = temas; }

    public void mostrarTemas() {
        System.out.println("Consulta: " + nombre);
        for (int i = 0; i < temas.size(); i++) {
            System.out.println((i + 1) + ". " + temas.get(i));
        }
    }

    @Override
    public String toString() {
        return "Consulta{nombre=" + nombre + ", fecha=" + fecha + ", descripcion=" + descripcion + ", temas=" + temas.size() + "}";
    }
}
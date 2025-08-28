import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaCiudadana {
    private String nombre;
    private LocalDate fecha;
    private String  descripcion;
    private List<Tema> temas;


    public ConsultaCiudadana(String nombre, LocalDate fecha, String descripcion) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        List<tema> temas = new ArrayList<>();
    }

    public void agregarTema(Tema tema) {
        temas.add(tema);
    }

    public void agregarTema(String nombre, String descripcion) {
        temas.add(new tema(nombre, descripcion));
    }

    public String getNombre() { 
        return nombre; 
        }

    public List<Tema> getTemas() { 
        return temas;
    }

    public void mostrarTemas() {
        system.out.println("Consulta:" + nombre);
        for (int i = 0; i < temas.size(); i++) {
            System.out.println((i + 1) + ". " + temas.get(i));
        }
    }

    public String toString() {
         return nombre + " - " + fecha + " (" + temas.size() + " temas)"; 
    }
}
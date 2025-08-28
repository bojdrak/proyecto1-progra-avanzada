public class Votante {
    private String rut;
    private String nombre;
    private int edad;
    private String direccion;

    public Votante(String rut, String nombre, int edad, String direccion) {
        this.rut = rut;
        this.nombre = nombre;
        this.edad = edad;
        this.direccion = direccion;
    }

    public String getRut() { 
        return rut; 
    }

    public String getNombre() { 
        return nombre;
    }

    public int getEdad() { 
        return edad; 
    }

    public String getDireccion() { 
        return direccion; 
    }

    public String toString() { 
        return nombre + " (" + rut + ")"; 
    }
}
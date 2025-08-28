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

    // Getters y Setters
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return "Votante{" + "rut=" + rut + ", nombre=" + nombre +
                ", edad=" + edad + ", direccion=" + direccion + '}';
    }
}
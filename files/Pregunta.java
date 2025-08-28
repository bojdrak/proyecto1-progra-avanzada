public class Pregunta { // Clase que representa una pregunta dentro de un tema de la consulta
    private String texto;
    private String tipoRespuesta;

    public Pregunta(String texto, String tipoRespuesta) {
        this.texto = texto;
        this.tipoRespuesta = tipoRespuesta;
    }

    // Getters y Setters
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getTipoRespuesta() { return tipoRespuesta; }
    public void setTipoRespuesta(String tipoRespuesta) { this.tipoRespuesta = tipoRespuesta; }

    @Override
    public String toString() {
        return "Pregunta: " + texto + " (" + tipoRespuesta + ")";
    }
}
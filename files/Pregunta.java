public class Pregunta {
    private String texto;
    private String tipoRespuesta;

    public Pregunta(String texto, String tipoRespuesta) {
        this.texto = texto;
        this.tipoRespuesta = tipoRespuesta;

    }
    public String getTexto() {
        return texto;
    }
    public String getTipoRespuesta() {
        return tipoRespuesta; 
    }
    public String toString() {
         return "Pregunta: " + texto + " (" + tipoRespuesta + ")"; 
        }
}
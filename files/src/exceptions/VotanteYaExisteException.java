package exceptions;

public class VotanteYaExisteException extends Exception {
    public VotanteYaExisteException(String mensaje) {
        super(mensaje);
    }
}
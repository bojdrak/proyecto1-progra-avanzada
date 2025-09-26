package exceptions;
// SIA2.9 creacion de la clase extend Exception
public class VotanteYaExisteException extends Exception {
    public VotanteYaExisteException(String mensaje) {
        super(mensaje);
    }
}
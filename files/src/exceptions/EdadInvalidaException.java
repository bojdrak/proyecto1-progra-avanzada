package exceptions;
// SIA2.9 creacion de la clase extend Exception
public class EdadInvalidaException extends Exception {
    public EdadInvalidaException(String mensaje) {
        super(mensaje);
    }
}
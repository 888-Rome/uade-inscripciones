package uade.inscripciones.base.exception;

/** Conflicto de estado -> 409. */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
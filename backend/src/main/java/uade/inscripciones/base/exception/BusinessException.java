package uade.inscripciones.base.exception;

/** Regla de negocio violada -> 400. */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

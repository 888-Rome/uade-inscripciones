package uade.inscripciones.base.exception;

/** Recurso inexistente -> 404. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String recurso, String campo, Object valor) {
        super(String.format("%s no encontrado con %s: '%s'", recurso, campo, valor));
    }
}
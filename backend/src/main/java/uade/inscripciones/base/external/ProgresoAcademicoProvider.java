package uade.inscripciones.base.external;

import java.util.Set;

/**
 * Progreso académico del alumno (materias aprobadas).
 * Dato EXTERNO: pertenece al padrón de UADE. El sistema lo CONSUME de
 * solo-lectura para ordenar/filtrar la oferta; no lo gestiona ni lo persiste
 * como parte del dominio.
 */

public interface ProgresoAcademicoProvider {
    /** Códigos de materias aprobadas (p. ej. "3.4.077"). */
    Set<String> materiasAprobadas(String legajo);
}

package uade.inscripciones.logic.strategy;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import org.springframework.stereotype.Component;
import uade.inscripciones.base.enums.CriterioOrdenEnum;
import uade.inscripciones.base.model.Clase;
import java.util.Comparator;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Interés Alumno ⌝ + Strategy Pattern
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
/** Devuelve las clases que el alumno necesita primero. */
@Component
public class OrdenPorInteresAlumno implements OrdenClasesStrategy {
    @Override public CriterioOrdenEnum criterio() { return CriterioOrdenEnum.INTERES_ALUMNO; }
    @Override
    public Comparator<Clase> comparador() {
        return Comparator
                .comparing(Clase::vacantes).reversed()
                .thenComparing(Clase::getSede);
    }
}
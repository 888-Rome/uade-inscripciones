package uade.inscripciones.logic.strategy;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import org.springframework.stereotype.Component;
import uade.inscripciones.base.enums.CriterioOrdenEnum;
import uade.inscripciones.base.model.Clase;
import java.util.Comparator;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Vacantes ⌝ + Strategy Pattern
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
/** Devuelve las clases con más vacantes primero. */
@Component
public class OrdenPorVacantes implements OrdenClasesStrategy {
    @Override public CriterioOrdenEnum criterio() { return CriterioOrdenEnum.VACANTES; }
    @Override public Comparator<Clase> comparador() { return Comparator.comparingInt(Clase::vacantes).reversed(); }
}
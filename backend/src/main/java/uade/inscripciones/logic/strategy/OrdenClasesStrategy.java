package uade.inscripciones.logic.strategy;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import uade.inscripciones.base.enums.CriterioOrdenEnum;
import uade.inscripciones.base.model.Clase;
import java.util.Comparator;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Strategy Pattern ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
public interface OrdenClasesStrategy {
    CriterioOrdenEnum criterio();
    Comparator<Clase> comparador();
}

package uade.inscripciones.base.external;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Set;

/**
 * Simula el padrón de UADE en el prototipo. Poblado por seed,
 * NUNCA escrito por la lógica de negocio (contrato de solo-lectura).
 * In-memory a propósito: no agrega tabla → más liviano.
 */
@Component
public class ProgresoAcademicoSeedAdapter implements ProgresoAcademicoProvider {

    private final Map<String, Set<String>> padron = Map.of(
            "1186512", Set.of("3.2.178", "3.1.024", "3.1.051") // ejemplo
    );

    @Override
    public Set<String> materiasAprobadas(String legajo) {
        return padron.getOrDefault(legajo, Set.of());
    }
}
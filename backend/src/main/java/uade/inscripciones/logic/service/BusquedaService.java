package uade.inscripciones.logic.service;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import uade.inscripciones.base.enums.CriterioOrdenEnum;
import uade.inscripciones.base.exception.BusinessException;
import uade.inscripciones.base.external.ProgresoAcademicoProvider;
import uade.inscripciones.base.external.ProgresoAcademicoSeedAdapter;
import uade.inscripciones.base.model.Alumno;
import uade.inscripciones.base.model.Clase;
import uade.inscripciones.base.model.Materia;
import uade.inscripciones.logic.strategy.OrdenClasesStrategy;
import uade.inscripciones.repository.ClaseRepository;
import uade.inscripciones.repository.MateriaRepository;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ BusquedaService ⌝ + Strategy Pattern
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Service
public class BusquedaService {

    public BusquedaService(ClaseRepository claseRepository,
                           List<OrdenClasesStrategy> estrategiasList,
                           ProgresoAcademicoProvider progreso, MateriaRepository materiaRepository) {
        this.claseRepository = claseRepository;
        this.materiaRepository = materiaRepository;
        this.progreso = progreso;
        this.estrategias = estrategiasList.stream()
                .collect(Collectors.toMap(OrdenClasesStrategy::criterio, s -> s));
    }

    private final ClaseRepository claseRepository;
    private final Map<CriterioOrdenEnum, OrdenClasesStrategy> estrategias;
    private final ProgresoAcademicoProvider progreso;
    private final MateriaRepository materiaRepository;

    public List<Clase> buscar(Alumno alumno, CriterioOrdenEnum criterio) {
        CriterioOrdenEnum efectivo = (criterio != null) ? criterio : CriterioOrdenEnum.INTERES_ALUMNO;
        OrdenClasesStrategy orden = estrategias.get(efectivo);
        if (orden == null) {
            throw new BusinessException("Criterio de orden no soportado: " + efectivo);
        }

        List<Materia> plan = alumno.getCarreras().get(0).getMaterias(); // TODO: multi-carrera?

        Set<String> aprobadas = progreso.materiasAprobadas(alumno.getLegajo());

        List<Materia> pendientes = plan.stream()
                .filter(m -> !aprobadas.contains(m.getCodigo()))
                .toList();

        if (pendientes.isEmpty()) {
            return List.of(); // TODO: el front muestra "¡terminaste!"
        }

        // filtro → la DB devuelve SOLO clases de materias pendientes
        List<Clase> resultados = claseRepository.findByMateriaIn(pendientes);
        // materiaId → su posición en el plan
        Map<Long, Integer> posicion = new HashMap<>();
        for (int i = 0; i < pendientes.size(); i++) {
            posicion.put(pendientes.get(i).getId(), i);
        }

        // ordena por posición en el plan, después Strategy.
        resultados.sort(
                Comparator.comparingInt((Clase c) -> posicion.get(c.getMateria().getId()))
                        .thenComparing(orden.comparador())
        );
        return resultados;
    }

    // TODO: Cambiar String por Materia?
    public List<Clase> buscarPorMateria(String texto) {
        List<Materia> materias =
                materiaRepository.findByNombreContainingIgnoreCase(texto);

        return claseRepository.findByMateriaIn(materias);
    }

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}
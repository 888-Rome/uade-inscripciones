package uade.inscripciones.repository;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import uade.inscripciones.base.enums.IdiomaEnum;
import uade.inscripciones.base.enums.OfrecimientoEnum;
import uade.inscripciones.base.enums.SedeEnum;
import uade.inscripciones.base.enums.TurnoEnum;
import uade.inscripciones.base.model.Clase;
import uade.inscripciones.base.model.Materia;

public interface ClaseRepository extends JpaRepository<Clase, Long> {
    List<Clase> findByMateriaIn(Collection<Materia> materias);
    // TODO: usar los query para los filtros.
    List<Clase> findByTurno(TurnoEnum turno);
    List<Clase> findBySede(SedeEnum sede);
    List<Clase> findByOfrecimiento(OfrecimientoEnum ofrecimiento);
    List<Clase> findByIdioma(IdiomaEnum idioma);
}
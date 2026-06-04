package uade.inscripciones.repository;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uade.inscripciones.base.model.Periodo;

@Repository
public interface PeriodoRepository  extends JpaRepository<Periodo, Long> {
}

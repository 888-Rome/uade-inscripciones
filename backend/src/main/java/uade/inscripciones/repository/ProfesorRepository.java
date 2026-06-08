package uade.inscripciones.repository;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import uade.inscripciones.base.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

}

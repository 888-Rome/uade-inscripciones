package uade.inscripciones.repository;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import org.springframework.data.jpa.repository.JpaRepository;
import uade.inscripciones.base.model.Carrera;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
}

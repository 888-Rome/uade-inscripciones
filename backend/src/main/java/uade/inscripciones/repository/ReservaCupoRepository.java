package uade.inscripciones.repository;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import org.springframework.data.jpa.repository.JpaRepository;
import uade.inscripciones.base.model.ReservaCupo;

public interface ReservaCupoRepository extends JpaRepository<ReservaCupo,Long> {
}

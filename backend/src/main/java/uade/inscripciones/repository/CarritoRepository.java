package uade.inscripciones.repository;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import org.springframework.data.jpa.repository.JpaRepository;
import uade.inscripciones.base.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}

package uade.inscripciones.repository;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import uade.inscripciones.base.model.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findByNombreContainingIgnoreCase(String nombre);
}
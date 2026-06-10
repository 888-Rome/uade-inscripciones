package uade.inscripciones.repository;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import org.springframework.data.jpa.repository.JpaRepository;
import uade.inscripciones.base.model.Alumno;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    Optional<Alumno> findByUsuarioEmail(String email);
}
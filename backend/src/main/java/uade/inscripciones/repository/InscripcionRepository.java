package uade.inscripciones.repository;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import uade.inscripciones.base.model.Inscripcion;
import uade.inscripciones.base.model.Alumno;
import uade.inscripciones.base.model.Clase;
import uade.inscripciones.base.enums.EstadoInscripcionEnum;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    long countByClaseAndEstado(Clase clase, EstadoInscripcionEnum estado);
    List<Inscripcion> findByAlumno(Alumno alumno);
}
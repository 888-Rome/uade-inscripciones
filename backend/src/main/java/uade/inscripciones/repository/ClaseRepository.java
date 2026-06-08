package uade.inscripciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uade.inscripciones.base.model.Clase;

public interface ClaseRepository extends JpaRepository<Clase, Long> {
}

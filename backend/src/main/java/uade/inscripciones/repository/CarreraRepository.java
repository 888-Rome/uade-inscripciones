package uade.inscripciones.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uade.inscripciones.base.model.Carrera;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
}

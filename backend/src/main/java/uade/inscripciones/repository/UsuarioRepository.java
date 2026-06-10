package uade.inscripciones.repository;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import uade.inscripciones.base.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
        boolean existsByEmail(String email);
}

package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import lombok.*;
import uade.inscripciones.base.enums.RolEnum;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Usuario ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class Usuario {

    // ▶ Identidad ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ▶ Credenciales ──────────────────────────────────────────────────────────────────────────────────────────────────
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private RolEnum rol;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}
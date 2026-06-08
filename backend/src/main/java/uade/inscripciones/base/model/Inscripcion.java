package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import uade.inscripciones.base.enums.EstadoInscripcionEnum;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Inscripcion ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class Inscripcion {

    // ▶ Identidad ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ▶ Relaciones ────────────────────────────────────────────────────────────────────────────────────────────────────
    @ManyToOne(optional = false)
    private Alumno alumno;

    @ManyToOne(optional = false)
    private Clase clase;

    // ▶ Estados ───────────────────────────────────────────────────────────────────────────────────────────────────────
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private EstadoInscripcionEnum estado;

    @Column(nullable = false)
    private LocalDateTime fechaInscripcion;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}
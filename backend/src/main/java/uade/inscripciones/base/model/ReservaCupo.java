package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ ReservaCupo ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class ReservaCupo {

    // ▶ Identidad ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ▶ Relaciones ────────────────────────────────────────────────────────────────────────────────────────────────────
    @ManyToOne(optional = false)
    private Carrito carrito;

    @ManyToOne(optional = false)
    private Clase clase;

    // ▶ Temporizador (no renovable) ───────────────────────────────────────────────────────────────────────────────────
    @Column(nullable = false)
    private LocalDateTime instanteInicio;

    @Column(nullable = false)
    private int duracionMinutos;

    // ▶ Derivados ─────────────────────────────────────────────────────────────────────────────────────────────────────
    public boolean estaVigente() {
        return instanteInicio.plusMinutes(duracionMinutos).isAfter(LocalDateTime.now());
    }

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}


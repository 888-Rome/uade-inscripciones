package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import uade.inscripciones.base.enums.TipoPeriodoEnum;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Periodo ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"ciclo_lectivo", "tipo_periodo"}))

public class Periodo {

    // ▶ Identidad ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cicloLectivo;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TipoPeriodoEnum tipoPeriodo;

    // ▶ Ventana Temporal ──────────────────────────────────────────────────────────────────────────────────────────────
    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}
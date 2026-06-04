package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import lombok.*;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ PERIODO ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cicloLectivo", "cuatrimestre"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class Periodo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cicloLectivo;

    @Column(nullable = false)
    private int cuatrimestre;
}

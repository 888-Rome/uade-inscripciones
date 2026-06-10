package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Carrera ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class Carrera {

    // ▶ Identidad ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String plan;

    // ▶ Relaciones ────────────────────────────────────────────────────────────────────────────────────────────────────
    /** Para evitar que Hibernate mezcle el orden en la DB, persistimos el índice con @OrderColumn. */
    @ManyToMany @OrderColumn(name="orden") @Builder.Default
    private List<Materia> materias = new ArrayList<>();

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}
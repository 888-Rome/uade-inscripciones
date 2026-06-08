package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Profesor ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class Profesor {

    // ▶ Identidad ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @Id
    private Long legajo;            // asignado, no autogenerado

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    // ▶ Relaciones ────────────────────────────────────────────────────────────────────────────────────────────────────
    @ManyToMany(mappedBy = "profesores") @Builder.Default
    private List<Clase> clases = new ArrayList<>();

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}
package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import uade.inscripciones.base.enums.RegimenEnum;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Materia ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class Materia {

    // ▶ Identidad ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    // ▶ Datos Académicos ──────────────────────────────────────────────────────────────────────────────────────────────
    @Column(nullable = false)
    private int cargaHoraria;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private RegimenEnum regimen;

    // ▶ Relaciones ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @OneToMany(mappedBy = "materia") @Builder.Default
    private List<Clase> clases = new ArrayList<>();

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}

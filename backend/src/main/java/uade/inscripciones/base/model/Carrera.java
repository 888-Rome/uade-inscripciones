package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ CARRERA ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class Carrera {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "carrera") @Builder.Default
    private List<Materia> materias = new ArrayList<>();
}

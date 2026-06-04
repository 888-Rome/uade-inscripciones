package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ PROFESOR ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class Profesor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legajo;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String nombreApellido;

    @OneToMany(mappedBy = "profesor") @Builder.Default
    private ArrayList<Materia> materias;
}

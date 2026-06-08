package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Carrito ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class Carrito {

    // ▶ Identidad ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ▶ Relaciones ────────────────────────────────────────────────────────────────────────────────────────────────────
    @OneToOne(optional = false)
    private Alumno alumno;          // Carrito dueño del 1—1 (FK alumno)

    @OneToMany(mappedBy = "carrito") @Builder.Default
    private List<ReservaCupo> reservas = new ArrayList<>();

    // ▶ Derivados ─────────────────────────────────────────────────────────────────────────────────────────────────────
    public boolean puedeAgregar() {     // restricción {máx 4} del UML
        return reservas.size() < 4;
    }

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}

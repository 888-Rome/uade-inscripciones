package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import uade.inscripciones.base.enums.EstadoInscripcionEnum;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Alumno ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class Alumno {

    // ▶ Identidad ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @Id
    private String legajo;

    @Column(nullable = false, unique = true)
    private String dni;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    // ▶ Autenticación ─────────────────────────────────────────────────────────────────────────────────────────────────
    @OneToOne(optional = false)
    private Usuario usuario;        // Alumno dueño del 1—1; Usuario no referencia de vuelta

    // ▶ Académico ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @ManyToMany @Builder.Default
    private List<Carrera> carreras = new ArrayList<>();          // dueño → tabla alumno_carrera

    @OneToMany(mappedBy = "alumno") @Builder.Default
    private List<Inscripcion> inscripciones = new ArrayList<>();

    // ▶ Carrito / Avisos ──────────────────────────────────────────────────────────────────────────────────────────────
    @OneToOne(mappedBy = "alumno")
    private Carrito carrito;        // Carrito es dueño del 1—1

    @OneToMany(mappedBy = "alumno") @Builder.Default
    private List<Notificacion> notificaciones = new ArrayList<>();

    // ▶ Derivados ─────────────────────────────────────────────────────────────────────────────────────────────────────
    public List<Clase> clasesActuales() {
        return inscripciones.stream()
                .filter(i -> i.getEstado() == EstadoInscripcionEnum.CONFIRMADA)
                .map(Inscripcion::getClase)
                .toList();
    }

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}
package uade.inscripciones.base.model;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;
import uade.inscripciones.base.enums.*;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ Clase ⌝
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class Clase {

    // ▶ Identidad ─────────────────────────────────────────────────────────────────────────────────────────────────────
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ▶ Oferta  ───────────────────────────────────────────────────────────────────────────────────────────────────────
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private SedeEnum sede;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TurnoEnum turno;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private ModalidadEnum modalidad;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private OfrecimientoEnum ofrecimiento;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private InstanciaEnum instancia;

    private String idioma;          // "ESPAÑOL" — String, no hay IdiomaEnum

    @Column(nullable = false)
    private int cupo;

    // ▶ Horario / Fechas ──────────────────────────────────────────────────────────────────────────────────────────────
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private List<DiaSemanaEnum> dias = new ArrayList<>();

    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    // ▶ Relaciones ────────────────────────────────────────────────────────────────────────────────────────────────────
    @ManyToOne(optional = false)
    private Materia materia;

    @ManyToOne(optional = false)
    private Periodo periodo;

    @ManyToMany @Builder.Default
    private List<Profesor> profesores = new ArrayList<>();       // dueño → tabla clase_profesor

    @OneToMany(mappedBy = "clase") @Builder.Default
    private List<Inscripcion> inscripciones = new ArrayList<>(); // para vacantes()

    // ▶ Derivados ─────────────────────────────────────────────────────────────────────────────────────────────────────
    public int vacantes() {
        long confirmadas = inscripciones.stream()
                .filter(i -> i.getEstado() == EstadoInscripcionEnum.CONFIRMADA)
                .count();
        return cupo - (int) confirmadas;
    }

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
}
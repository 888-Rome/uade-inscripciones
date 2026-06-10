package uade.inscripciones.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import uade.inscripciones.base.model.Clase;
import uade.inscripciones.base.model.Inscripcion;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class InscripcionDTO {
    private Long id;
    private String estado;
    private LocalDateTime fechaInscripcion;
    private Long claseId;
    private String materiaCodigo;
    private String materiaNombre;
    private String sede;
    private String turno;
    private String modalidad;
    private List<String> dias;
    private String horaInicio;
    private String horaFin;
    private int vacantes;
    private List<String> profesores;

    public static InscripcionDTO from(Inscripcion i) {
        Clase c = i.getClase();
        return new InscripcionDTO(
                i.getId(),
                i.getEstado().name(),
                i.getFechaInscripcion(),
                c.getId(),
                c.getMateria().getCodigo(),
                c.getMateria().getNombre(),
                c.getSede().name(),
                c.getTurno().name(),
                c.getModalidad().name(),
                c.getDias().stream().map(Enum::name).toList(),
                c.getHoraInicio() != null ? c.getHoraInicio().toString() : null,
                c.getHoraFin() != null ? c.getHoraFin().toString() : null,
                c.vacantes(),
                c.getProfesores().stream().map(p -> p.getNombres() + " " + p.getApellidos()).toList()
        );
    }
}
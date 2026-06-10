package uade.inscripciones.logic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uade.inscripciones.api.dto.response.InscripcionDTO;
import uade.inscripciones.base.enums.EstadoInscripcionEnum;
import uade.inscripciones.base.enums.TipoPeriodoEnum;
import uade.inscripciones.base.exception.BusinessException;
import uade.inscripciones.base.exception.ConflictException;
import uade.inscripciones.base.exception.ResourceNotFoundException;
import uade.inscripciones.base.model.Alumno;
import uade.inscripciones.base.model.Clase;
import uade.inscripciones.base.model.Inscripcion;
import uade.inscripciones.repository.AlumnoRepository;
import uade.inscripciones.repository.ClaseRepository;
import uade.inscripciones.repository.InscripcionRepository;
import uade.inscripciones.repository.PeriodoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final ClaseRepository claseRepository;
    private final AlumnoRepository alumnoRepository;
    private final PeriodoRepository periodoRepository;

    // UC2 — ver inscripciones actuales (no muestra anuladas)
    @Transactional(readOnly = true)
    public List<InscripcionDTO> listar(String email) {
        Alumno alumno = alumno(email);
        return inscripcionRepository.findByAlumno(alumno).stream()
                .filter(i -> i.getEstado() != EstadoInscripcionEnum.ANULADA)
                .map(InscripcionDTO::from)
                .toList();
    }

    // UC4 — baja dentro del período habilitado (409 si no hay período de baja vigente)
    @Transactional
    public void baja(String email, Long inscripcionId) {
        Inscripcion ins = inscripcionDelAlumno(alumno(email), inscripcionId);
        if (!hayPeriodoVigente(TipoPeriodoEnum.BAJA)) {
            throw new ConflictException("No hay un período de baja vigente.");
        }
        ins.setEstado(EstadoInscripcionEnum.ANULADA);
        inscripcionRepository.save(ins);
    }

    // UC5 — swap atómico: si el destino no tiene cupo, no se toca la original
    @Transactional
    public InscripcionDTO swap(String email, Long inscripcionId, Long claseDestinoId) {
        Alumno alumno = alumno(email);
        Inscripcion original = inscripcionDelAlumno(alumno, inscripcionId);
        Clase destino = claseRepository.findById(claseDestinoId)
                .orElseThrow(() -> new ResourceNotFoundException("Clase", "id", claseDestinoId));

        if (!destino.getMateria().getId().equals(original.getClase().getMateria().getId())) {
            throw new BusinessException("La clase destino debe ser de la misma materia.");
        }
        if (destino.vacantes() <= 0) {
            throw new ConflictException("La clase destino no tiene vacantes."); // original intacta
        }

        original.setEstado(EstadoInscripcionEnum.ANULADA);
        inscripcionRepository.save(original);

        Inscripcion nueva = Inscripcion.builder()
                .alumno(alumno)
                .clase(destino)
                .estado(EstadoInscripcionEnum.CONFIRMADA)
                .fechaInscripcion(LocalDateTime.now())
                .build();
        return InscripcionDTO.from(inscripcionRepository.save(nueva));
    }

    // ── helpers ──
    private Alumno alumno(String email) {
        return alumnoRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno", "email", email));
    }

    private Inscripcion inscripcionDelAlumno(Alumno alumno, Long inscripcionId) {
        Inscripcion ins = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripcion", "id", inscripcionId));
        if (!ins.getAlumno().getLegajo().equals(alumno.getLegajo())) {
            throw new ResourceNotFoundException("Inscripcion", "id", inscripcionId); // no exponer ajenas
        }
        return ins;
    }

    private boolean hayPeriodoVigente(TipoPeriodoEnum tipo) {
        LocalDate hoy = LocalDate.now();
        return periodoRepository.findAll().stream()
                .filter(p -> p.getTipoPeriodo() == tipo)
                .anyMatch(p -> !hoy.isBefore(p.getFechaInicio()) && !hoy.isAfter(p.getFechaFin()));
    }
}
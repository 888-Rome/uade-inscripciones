package uade.inscripciones.base.config;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uade.inscripciones.base.model.*;
import uade.inscripciones.base.enums.*;
import uade.inscripciones.repository.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ DATA-SEEDER ⌝
// BD H2 en memoria (se recrea en cada arranque). Carga una cadena demo completa:
// usuario → carrera+materias (en orden de plan) → profesores → períodos → clases →
// alumno → carrito → 1 inscripción confirmada → 1 reserva activa.
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository     usuarioRepository;
    private final CarreraRepository     carreraRepository;
    private final MateriaRepository     materiaRepository;
    private final ProfesorRepository    profesorRepository;
    private final PeriodoRepository     periodoRepository;
    private final ClaseRepository       claseRepository;
    private final AlumnoRepository      alumnoRepository;
    private final CarritoRepository     carritoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final ReservaCupoRepository reservaCupoRepository;
    private final PasswordEncoder       passwordEncoder;

    @Override
    public void run(String... args) {

        // ⌞ USUARIO ⌝ — login por email; contraseña HASHEADA (BCrypt)
        Usuario uAlumno = crearUsuario("alumno@uade.edu.ar", "password123", RolEnum.ALUMNO);
        crearUsuario("admin@uade.edu.ar", "password123", RolEnum.ADMIN);

        // ⌞ MATERIA ⌝ — se guardan primero (Carrera es dueña del N—N y necesita sus ids)
        Materia algebra   = guardarMateria("3.1.051", "ÁLGEBRA",                  85, RegimenEnum.FINAL_OBLIGATORIO);
        Materia matDisc   = guardarMateria("3.1.024", "MATEMÁTICA DISCRETA",      68, RegimenEnum.PROMOCION);
        Materia quimica   = guardarMateria("3.2.178", "FUNDAMENTOS DE QUÍMICA",   68, RegimenEnum.PROMOCION);
        Materia fisica1   = guardarMateria("3.1.052", "FÍSICA I",                119, RegimenEnum.PROMOCION);
        Materia prog3     = guardarMateria("3.4.077", "PROGRAMACIÓN III",         68, RegimenEnum.FINAL_OBLIGATORIO);
        Materia teleinfo  = guardarMateria("3.4.212", "TELEINFORMÁTICA Y REDES",  68, RegimenEnum.FINAL_OBLIGATORIO);

        // ⌞ CARRERA ⌝ — la lista define el ORDEN de plan (lo usa el ordering por progreso)
        Carrera informatica = Carrera.builder()
                .nombre("Ingeniería en Informática")
                .plan("1621")
                .materias(List.of(algebra, matDisc, quimica, fisica1, prog3, teleinfo))
                .build();
        carreraRepository.save(informatica);

        // ⌞ PROFESOR ⌝ — legajo es @Id asignado a mano (sin @GeneratedValue)
        Profesor profe1 = guardarProfesor("10001", "20123456", "Moisés Evaristo", "Bueno");
        Profesor profe2 = guardarProfesor("10002", "22124576", "Nicolás Alejandro", "Rossi");
        Profesor profe3 = guardarProfesor("10003", "34126476", "Nahuel", "Gonzáles");

        // ⌞ PERIODO ⌝ — vigentes hoy para que /periodos/vigentes devuelva datos
        LocalDate hoy = LocalDate.now();
        guardarPeriodo(2026, TipoPeriodoEnum.INSCRIPCION, hoy.minusDays(3), hoy.plusDays(30));
        guardarPeriodo(2026, TipoPeriodoEnum.BAJA,        hoy.minusDays(3), hoy.plusDays(30));
        Periodo cursada2026 = guardarPeriodo(2026, TipoPeriodoEnum.CURSADA, hoy.minusDays(3), hoy.plusDays(120));

        // ⌞ CLASE ⌝ — Clase es dueña del N—N con Profesor; referencia Materia y Periodo
        // Dos clases de Álgebra (mismo materia) habilitan la demo de swap
        Clase algebraMan = guardarClase(algebra, cursada2026, List.of(profe1),
                SedeEnum.LIMA, TurnoEnum.MANIANA, 30, List.of(DiaSemanaEnum.LUN, DiaSemanaEnum.MIE),
                LocalTime.of(8, 0), LocalTime.of(12, 0));

        Clase algebraNoc = guardarClase(algebra, cursada2026, List.of(profe2),
                SedeEnum.RECOLETA, TurnoEnum.NOCHE, 5, List.of(DiaSemanaEnum.MAR, DiaSemanaEnum.JUE),
                LocalTime.of(19, 0), LocalTime.of(23, 0));

        Clase prog3Tar = guardarClase(prog3, cursada2026, List.of(profe3),
                SedeEnum.LIMA, TurnoEnum.TARDE, 40, List.of(DiaSemanaEnum.VIE),
                LocalTime.of(14, 0), LocalTime.of(18, 0));

        Clase teleinfoInt = guardarClase(teleinfo, cursada2026, List.of(profe1, profe2),
                SedeEnum.PINAMAR, TurnoEnum.MANIANA, 3,
                List.of(DiaSemanaEnum.LUN, DiaSemanaEnum.MAR, DiaSemanaEnum.MIE,
                        DiaSemanaEnum.JUE, DiaSemanaEnum.VIE, DiaSemanaEnum.SAB),
                LocalTime.of(7, 30), LocalTime.of(17, 30));

        // ⌞ ALUMNO ⌝ — dueño del 1—1 con Usuario y del N—N con Carrera
        Alumno ximena = Alumno.builder()
                .legajo("1186512")
                .dni("43222111")
                .nombres("Ximena Stella")
                .apellidos("Romero")
                .usuario(uAlumno)
                .carreras(List.of(informatica))
                .build();
        alumnoRepository.save(ximena);

        // ⌞ CARRITO ⌝ — Carrito es dueño del 1—1 (FK alumno)
        Carrito carrito = Carrito.builder().alumno(ximena).build();
        carritoRepository.save(carrito);

        // ⌞ INSCRIPCIÓN ⌝ — 1 confirmada → aparece en UC2 y descuenta vacantes() de algebraMan
        inscripcionRepository.save(Inscripcion.builder()
                .alumno(ximena)
                .clase(algebraMan)
                .estado(EstadoInscripcionEnum.CONFIRMADA)
                .fechaInscripcion(LocalDateTime.now())
                .build());

        // ⌞ RESERVA-CUPO ⌝ — 1 reserva activa en el carrito (15 min)
        reservaCupoRepository.save(ReservaCupo.builder()
                .carrito(carrito)
                .clase(prog3Tar)
                .instanteInicio(LocalDateTime.now())
                .duracionMinutos(15)
                .build());

        // ⌞ NOTIFICACIÓN ⌝ — NO se siembra: TipoNotificacionEnum está vacío y tipo es nullable=false.
        //                     Agregá valores al enum y descomentá un bloque acá si querés seed.
    }

    // ─── helpers ─────────────────────────────────────────────────────────────────────────────────────────────────────
    private Usuario crearUsuario(String email, String passwordPlano, RolEnum rol) {
        if (usuarioRepository.existsByEmail(email)) {
            return usuarioRepository.findByEmail(email).orElseThrow();
        }
        return usuarioRepository.save(Usuario.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(passwordPlano))
                .rol(rol)
                .build());
    }

    private Materia guardarMateria(String codigo, String nombre, int cargaHoraria, RegimenEnum regimen) {
        return materiaRepository.save(Materia.builder()
                .codigo(codigo).nombre(nombre).cargaHoraria(cargaHoraria).regimen(regimen).build());
    }

    private Profesor guardarProfesor(String legajo, String dni, String nombres, String apellidos) {
        return profesorRepository.save(Profesor.builder()
                .legajo(legajo).dni(dni).nombres(nombres).apellidos(apellidos).build());
    }

    private Periodo guardarPeriodo(int ciclo, TipoPeriodoEnum tipo, LocalDate desde, LocalDate hasta) {
        return periodoRepository.save(Periodo.builder()
                .cicloLectivo(ciclo).tipoPeriodo(tipo).fechaInicio(desde).fechaFin(hasta).build());
    }

    private Clase guardarClase(Materia materia, Periodo periodo, List<Profesor> profesores,
                               SedeEnum sede, TurnoEnum turno, int cupo, List<DiaSemanaEnum> dias,
                               LocalTime horaInicio, LocalTime horaFin) {
        return claseRepository.save(Clase.builder()
                .materia(materia)
                .periodo(periodo)
                .profesores(profesores)
                .sede(sede)
                .turno(turno)
                .modalidad(ModalidadEnum.PRESENCIAL)
                .ofrecimiento(OfrecimientoEnum.CURRICULAR)
                .instancia(InstanciaEnum.REGULAR)
                .idioma("ESPAÑOL")
                .cupo(cupo)
                .dias(dias)
                .horaInicio(horaInicio)
                .horaFin(horaFin)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusMonths(4))
                .build());
    }
}
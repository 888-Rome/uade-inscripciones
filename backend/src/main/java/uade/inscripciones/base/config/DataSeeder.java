package uade.inscripciones.base.config;

// ▶ Imports ═══════════════════════════════════════════════════════════════════════════════════════════════════════════
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uade.inscripciones.base.model.*;
import uade.inscripciones.repository.*;
import lombok.RequiredArgsConstructor;

// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
// ⌞ DATA-SEEDER ⌝
// Trabajamos con una BD H2 en memoria. El DataSeeder se ocupa de cargar datos
// al arrancar para que la App tenga con qué trabajar desde el primer momento.
// ═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProfesorRepository profesorRepository;
    private final CarreraRepository carreraRepository;
    private final PeriodoRepository periodoRepository;

    @Override
    public void run(String... args) throws Exception {
        // ⌞ CARRERA ⌝
        Carrera sistemas = Carrera.builder()
                .nombre("Ingeniería en Informática")
                .build();
        carreraRepository.save(sistemas);

        Carrera comercio = Carrera.builder()
                .nombre("Comercio Internacional")
                .build();
        carreraRepository.save(comercio);

        Carrera abogacia = Carrera.builder()
                .nombre("Abogacía")
                .build();
        carreraRepository.save(abogacia);

        // ⌞ PROFESOR ⌝
        Profesor profe1 = Profesor.builder()
                .dni("20123456")
                .nombreApellido("Moisés Evaristo Bueno")
                .build();
        profesorRepository.save(profe1);

        Profesor profe2 = Profesor.builder()
                .dni("22124576")
                .nombreApellido("Nicolás Alejandro Rossi")
                .build();
        profesorRepository.save(profe2);

        Profesor profe3 = Profesor.builder()
                .dni("34126476")
                .nombreApellido("Nahuel Gonzales")
                .build();
        profesorRepository.save(profe3);

        // ⌞ PERIODO ⌝
        Periodo periodo1 = Periodo.builder()
                .cicloLectivo(2024)
                .tipoPeriodo(1)
                .build();
        periodoRepository.save(periodo1);

        Periodo periodo2 = Periodo.builder()
                .cicloLectivo(2025)
                .tipoPeriodo(1)
                .build();
        periodoRepository.save(periodo2);

        Periodo periodo3 = Periodo.builder()
                .cicloLectivo(2026)
                .tipoPeriodo(1)
                .build();
        periodoRepository.save(periodo3);
    }
}

package uade.inscripciones.base.model;

import java.util.ArrayList;

public class Profesor {
    private Long legajo; // PK
    private String dni;
    private String nombreApellido;
    private ArrayList<Materia> materias;
}

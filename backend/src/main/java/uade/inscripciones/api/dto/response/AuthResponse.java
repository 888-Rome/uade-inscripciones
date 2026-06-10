package uade.inscripciones.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Respuesta de login. token + identidad mínima.
 * Si querés que el front muestre legajo/nombre sin otra llamada,
 * agregá esos campos acá y completalos en AuthController desde el Alumno.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String rol;
    // private String legajo;   // <- opcional, desde Alumno
    // private String nombre;   // <- opcional, desde Alumno
}

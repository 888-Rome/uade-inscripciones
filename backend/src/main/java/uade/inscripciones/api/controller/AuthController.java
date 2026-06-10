package uade.inscripciones.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import uade.inscripciones.api.dto.request.LoginRequest;
import uade.inscripciones.api.dto.response.AuthResponse;
import uade.inscripciones.base.model.Usuario;
import uade.inscripciones.security.JwtService;
import uade.inscripciones.security.UsuarioDetails;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    // UADE no tiene auto-registro: solo login. Las cuentas se siembran en el DataSeeder.
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        // Lanza BadCredentialsException (-> 401) si email/pass no matchean
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UsuarioDetails principal = (UsuarioDetails) userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generarToken(principal);

        Usuario usuario = principal.getUsuario();
        return ResponseEntity.ok(new AuthResponse(
                token,
                usuario.getEmail(),
                usuario.getRol().name()
        ));
    }
}


package uade.inscripciones.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uade.inscripciones.api.dto.response.InscripcionDTO;
import uade.inscripciones.logic.service.InscripcionService;
import uade.inscripciones.security.UsuarioDetails;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @GetMapping
    public List<InscripcionDTO> listar(@AuthenticationPrincipal UsuarioDetails principal) {
        return inscripcionService.listar(principal.getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> baja(@AuthenticationPrincipal UsuarioDetails principal,
                                     @PathVariable Long id) {
        inscripcionService.baja(principal.getUsername(), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/swap/{claseDestinoId}")
    public InscripcionDTO swap(@AuthenticationPrincipal UsuarioDetails principal,
                               @PathVariable Long id,
                               @PathVariable Long claseDestinoId) {
        return inscripcionService.swap(principal.getUsername(), id, claseDestinoId);
    }
}
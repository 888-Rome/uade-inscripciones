package uade.inscripciones.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uade.inscripciones.base.model.Usuario; // <-- ajustar al package real de tu entidad
import java.util.Collection;
import java.util.List;

/**
 * Envuelve la entidad de dominio Usuario para Spring Security.
 * Mantiene Usuario limpio (sin acoplamiento a Spring).
 * El "username" de Spring = email institucional.
 */
@RequiredArgsConstructor
public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
    }

    @Override public String getPassword()              { return usuario.getPasswordHash(); }
    @Override public String getUsername()              { return usuario.getEmail(); }
    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }
}

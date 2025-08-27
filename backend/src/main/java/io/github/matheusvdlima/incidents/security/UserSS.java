package io.github.matheusvdlima.incidents.security;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserSS(
        UUID id,
        String email,
        String senha,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails {

    public UserSS(UUID id, String email, String senha, String perfil) {
        this(id, email, senha, List.of(new SimpleGrantedAuthority(perfil)));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

}

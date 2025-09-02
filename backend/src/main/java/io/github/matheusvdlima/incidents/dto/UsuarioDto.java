package io.github.matheusvdlima.incidents.dto;

import io.github.matheusvdlima.incidents.enums.PerfilEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class UsuarioDto {
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    private PerfilEnum perfil;
    private LocalDateTime dataCriacao;

    public Map<String, String> getPerfil() {
        if (perfil == null) return null;
        return Map.of(
                "code", perfil.name(),
                "label", perfil.getLabel()
        );
    }
}

package io.github.matheusvdlima.incidents.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum PerfilEnum {

    GESTOR("GESTOR", "Gestor"),
    USUARIO("USUARIO", "Usuário");

    private final String code;
    private final String label;

    public static PerfilEnum fromCode(String code) {
        return Arrays.stream(values())
                .filter(p -> p.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Perfil inválido."));
    }
}

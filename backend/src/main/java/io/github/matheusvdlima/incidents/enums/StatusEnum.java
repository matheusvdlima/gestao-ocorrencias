package io.github.matheusvdlima.incidents.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum StatusEnum {

    ABERTA("ABERTA", "Aberta"),
    EM_ANDAMENTO("EM_ANDAMENTO", "Em andamento"),
    RESOLVIDA("RESOLVIDA", "Resolvida"),
    CANCELADA("CANCELADA", "Cancelada");

    private final String code;
    private final String label;

    public static StatusEnum fromCode(String code) {
        return Arrays.stream(values())
                .filter(s -> s.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status inválido."));
    }
}

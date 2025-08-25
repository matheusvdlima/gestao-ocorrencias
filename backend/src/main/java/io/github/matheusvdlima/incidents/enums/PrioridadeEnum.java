package io.github.matheusvdlima.incidents.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum PrioridadeEnum {

    BAIXA("BAIXA", "Baixa"),
    MEDIA("MEDIA", "Média"),
    ALTA("ALTA", "Alta");

    private final String code;
    private final String label;

    public static PrioridadeEnum fromCode(String code) {
        return Arrays.stream(values())
                .filter(p -> p.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código inválido: " + code));
    }
}

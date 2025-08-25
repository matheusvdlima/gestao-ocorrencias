package io.github.matheusvdlima.incidents.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultadoDto {
    private String chave;
    private Long valor;
}

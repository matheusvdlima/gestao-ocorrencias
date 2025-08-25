package io.github.matheusvdlima.incidents.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EstatisticaDto {
    private Long total;
    private List<ResultadoDto> status;
    private List<ResultadoDto> prioridade;
}

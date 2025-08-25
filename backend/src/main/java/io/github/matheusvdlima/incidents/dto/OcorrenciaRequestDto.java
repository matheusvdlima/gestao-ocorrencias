package io.github.matheusvdlima.incidents.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcorrenciaRequestDto {
    @NotBlank
    @Size(max = 120)
    private String titulo;

    private String descricao;

    @NotBlank
    private String prioridade;

    @NotBlank
    private String status;

    @NotBlank
    @Size(max = 255)
    private String emailResponsavel;

    private String[] tags;
}

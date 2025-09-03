package io.github.matheusvdlima.incidents.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioRequestDto {
    @NotBlank
    @Size(max = 120)
    private String autor;

    @NotBlank
    private String mensagem;
}

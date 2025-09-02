package io.github.matheusvdlima.incidents.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioShortRequestDto {
    @NotBlank
    @Size(max = 150)
    private String nome;

    @NotBlank
    @Size(max = 120)
    private String email;
}

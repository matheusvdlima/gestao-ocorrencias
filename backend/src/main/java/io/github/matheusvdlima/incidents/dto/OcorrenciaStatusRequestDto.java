package io.github.matheusvdlima.incidents.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcorrenciaStatusRequestDto {
    @NotBlank
    private String status;
}

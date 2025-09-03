package io.github.matheusvdlima.incidents.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorDetail {
    @Schema(example = "titulo")
    private String field;

    @Schema(example = "não deve estar em branco")
    private String message;
}

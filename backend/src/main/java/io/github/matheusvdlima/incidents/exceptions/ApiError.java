package io.github.matheusvdlima.incidents.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ApiError", description = "Envelope padrão de erros da API")
public class ApiError {

    @Schema(example = "2025-09-02T11:22:33.123-03:00")
    private OffsetDateTime timestamp;

    @Schema(example = "422")
    private int status;

    @Schema(example = "Unprocessable Entity")
    private String error;

    @Schema(example = "ERR_VALIDATION")
    private String code;

    @Schema(example = "Dados inválidos.")
    private String message;

    @Schema(example = "/incidents")
    private String path;

    @Schema(example = "0b0e2e8e-01c8-4d84-b5d0-2b3c3b1b2c8f", description = "ID de correlação por requisição")
    private String correlationId;

    private List<ApiErrorDetail> details;

    public static ApiError of(int status, String error, String code, String message, String path, String correlationId, List<ApiErrorDetail> details) {
        return ApiError.builder()
                .timestamp(OffsetDateTime.now())
                .status(status)
                .error(error)
                .code(code)
                .message(message)
                .path(path)
                .correlationId(correlationId)
                .details(details)
                .build();
    }
}

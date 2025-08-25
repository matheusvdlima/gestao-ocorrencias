package io.github.matheusvdlima.incidents.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ComentarioDto {
    private UUID id;
    private UUID idOcorrencia;
    private String autor;
    private String mensagem;
    private LocalDateTime dataCriacao;
}

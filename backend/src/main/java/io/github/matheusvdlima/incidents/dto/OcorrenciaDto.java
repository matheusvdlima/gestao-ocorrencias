package io.github.matheusvdlima.incidents.dto;

import io.github.matheusvdlima.incidents.enums.PrioridadeEnum;
import io.github.matheusvdlima.incidents.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class OcorrenciaDto {
    private UUID id;
    private String titulo;
    private String descricao;
    private PrioridadeEnum prioridade;
    private StatusEnum status;
    private String emailResponsavel;
    private String[] tags;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataAtualizacao;

    public Map<String, String> getPrioridade() {
        if (prioridade == null) return null;
        return Map.of(
                "code", prioridade.name(),
                "label", prioridade.getLabel()
        );
    }

    public Map<String, String> getStatus() {
        if (status == null) return null;
        return Map.of(
                "code", status.name(),
                "label", status.getLabel()
        );
    }
}

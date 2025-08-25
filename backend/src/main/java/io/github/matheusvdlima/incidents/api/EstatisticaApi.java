package io.github.matheusvdlima.incidents.api;

import io.github.matheusvdlima.incidents.dto.EstatisticaDto;
import io.github.matheusvdlima.incidents.service.OcorrenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
@Tag(name = "stats", description = "Estatísticas API")
public class EstatisticaApi {

    private final OcorrenciaService ocorrenciaService;

    @GetMapping("/incidents")
    @Operation(summary = "Retorna estatísticas agregadas de ocorrências")
    public ResponseEntity<EstatisticaDto> estatisticas() {
        return ResponseEntity.ok(ocorrenciaService.obterEstatisticas());
    }
}

package io.github.matheusvdlima.incidents.api;

import io.github.matheusvdlima.incidents.dto.DominioDto;
import io.github.matheusvdlima.incidents.enums.PerfilEnum;
import io.github.matheusvdlima.incidents.enums.PrioridadeEnum;
import io.github.matheusvdlima.incidents.enums.StatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/dominios")
@Tag(name = "dominios", description = "Domínios API")
public class DominioApi {

    @Operation(summary = "Lista todos os status")
    @GetMapping("/status")
    public List<DominioDto> listarStatus() {
        return Arrays.stream(StatusEnum.values())
                .map(s -> new DominioDto(s.getCode(), s.getLabel()))
                .toList();
    }

    @Operation(summary = "Lista todas as prioridades")
    @GetMapping("/prioridades")
    public List<DominioDto> listarPrioridades() {
        return Arrays.stream(PrioridadeEnum.values())
                .map(p -> new DominioDto(p.getCode(), p.getLabel()))
                .toList();
    }

    @Operation(summary = "Lista todos os perfis")
    @GetMapping("/perfis")
    public List<DominioDto> listarPerfis() {
        return Arrays.stream(PerfilEnum.values())
                .map(p -> new DominioDto(p.getCode(), p.getLabel()))
                .toList();
    }
}

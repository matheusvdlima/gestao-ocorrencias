package io.github.matheusvdlima.incidents.api;

import io.github.matheusvdlima.incidents.dto.*;
import io.github.matheusvdlima.incidents.entity.Comentario;
import io.github.matheusvdlima.incidents.entity.Ocorrencia;
import io.github.matheusvdlima.incidents.mapper.ComentarioMapper;
import io.github.matheusvdlima.incidents.mapper.OcorrenciaMapper;
import io.github.matheusvdlima.incidents.service.ComentarioService;
import io.github.matheusvdlima.incidents.service.OcorrenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/incidents")
@RequiredArgsConstructor
@Tag(name = "incidents", description = "Ocorrências API")
public class OcorrenciaApi {

    private final OcorrenciaService ocorrenciaService;
    private final ComentarioService comentarioService;
    private final OcorrenciaMapper ocorrenciaMapper;
    private final ComentarioMapper comentarioMapper;

    @GetMapping
    @Operation(summary = "Lista ocorrências com paginação, filtro e ordenação")
    public ResponseEntity<PageResponse<OcorrenciaDto>> listar(
            @PageableDefault(page = 0, size = 10, sort = "dataAbertura", direction = Direction.DESC) @ParameterObject
            Pageable pageable,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String prioridade,
            @RequestParam(required = false) String texto
    ) {
        Page<Ocorrencia> ocorrencias = ocorrenciaService.listar(pageable, status, prioridade, texto);
        Page<OcorrenciaDto> dtoPage = ocorrencias.map(ocorrenciaMapper::toDTO);

        return ResponseEntity.ok(PageResponse.from(dtoPage));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca ocorrência pelo identificador")
    public ResponseEntity<OcorrenciaDto> buscarPorId(final @PathVariable UUID id) {
        Ocorrencia ocorrencia = ocorrenciaService.buscarPorId(id);
        return ResponseEntity.ok(ocorrenciaMapper.toDTO(ocorrencia));
    }

    @PostMapping
    @Operation(summary = "Cria nova ocorrência")
    public ResponseEntity<OcorrenciaDto> criar(@Valid @RequestBody OcorrenciaRequestDto dto) {
        Ocorrencia ocorrencia = ocorrenciaMapper.toEntity(dto);
        Ocorrencia salvo = ocorrenciaService.criar(ocorrencia);
        return ResponseEntity.ok(ocorrenciaMapper.toDTO(salvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza ocorrência")
    public ResponseEntity<OcorrenciaDto> atualizar(
            final @PathVariable UUID id,
            @Valid @RequestBody OcorrenciaRequestDto dto
    ) {
        Ocorrencia ocorrencia = ocorrenciaMapper.toEntity(dto);
        Ocorrencia atualizado = ocorrenciaService.atualizar(id, ocorrencia);
        return ResponseEntity.ok(ocorrenciaMapper.toDTO(atualizado));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza status da ocorrência")
    public ResponseEntity<OcorrenciaDto> alterarStatus(
            final @PathVariable UUID id,
            @Valid @RequestBody OcorrenciaStatusRequestDto dto
    ) {
        Ocorrencia ocorrencia = ocorrenciaService.alterarStatus(id, dto.getStatus());
        return ResponseEntity.ok(ocorrenciaMapper.toDTO(ocorrencia));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui ocorrência")
    public ResponseEntity<Void> deletar(final @PathVariable UUID id) {
        ocorrenciaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Lista comentários de uma ocorrência")
    public ResponseEntity<List<ComentarioDto>> listarComentarios(final @PathVariable UUID id) {
        List<Comentario> comentarios = comentarioService.listarPorOcorrencia(id);
        return ResponseEntity.ok(comentarioMapper.toDTOList(comentarios));
    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "Adiciona comentários em uma ocorrência")
    public ResponseEntity<List<ComentarioDto>> adicionarComentarios(
            final @PathVariable UUID id,
            @Valid @RequestBody List<ComentarioRequestDto> dtos
    ) {
        List<Comentario> comentarios = comentarioMapper.toEntityList(dtos);
        List<Comentario> salvos = comentarioService.adicionarComentarios(id, comentarios);
        return ResponseEntity.ok(comentarioMapper.toDTOList(salvos));
    }
}

package io.github.matheusvdlima.incidents.api;

import io.github.matheusvdlima.incidents.dto.PageResponse;
import io.github.matheusvdlima.incidents.dto.UsuarioDto;
import io.github.matheusvdlima.incidents.dto.UsuarioRequestDto;
import io.github.matheusvdlima.incidents.dto.UsuarioShortRequestDto;
import io.github.matheusvdlima.incidents.entity.Usuario;
import io.github.matheusvdlima.incidents.mapper.UsuarioMapper;
import io.github.matheusvdlima.incidents.service.UsuarioService;
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

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "users", description = "Usuários API")
public class UsuarioApi {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @GetMapping
    @Operation(summary = "Lista de usuários com paginação, filtro e ordenação")
    public ResponseEntity<PageResponse<UsuarioDto>> listar(
            @PageableDefault(page = 0, size = 10, sort = "nome", direction = Direction.ASC) @ParameterObject
            Pageable pageable,
            @RequestParam(required = false) String perfil,
            @RequestParam(required = false) String texto
    ) {
        Page<Usuario> usuarios = usuarioService.listar(pageable, perfil, texto);
        Page<UsuarioDto> dtoPage = usuarios.map(usuarioMapper::toDTO);

        return ResponseEntity.ok(PageResponse.from(dtoPage));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário pelo identificador")
    public ResponseEntity<UsuarioDto> buscarPorId(final @PathVariable UUID id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Busca usuário pelo e-mail")
    public ResponseEntity<UsuarioDto> buscarPorEmail(final @PathVariable String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));
    }

    @PostMapping
    @Operation(summary = "Cria novo usuário")
    public ResponseEntity<UsuarioDto> criar(@Valid @RequestBody UsuarioRequestDto dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario salvo = usuarioService.criar(usuario);
        return ResponseEntity.ok(usuarioMapper.toDTO(salvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza usuário")
    public ResponseEntity<UsuarioDto> atualizar(
            final @PathVariable UUID id,
            @Valid @RequestBody UsuarioShortRequestDto dto
    ) {
        Usuario atualizado = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(usuarioMapper.toDTO(atualizado));
    }

    @PatchMapping("/{id}/perfil")
    @Operation(summary = "Atualiza perfil do usuário")
    public ResponseEntity<UsuarioDto> alterarPerfil(
            final @PathVariable UUID id,
            @Valid @RequestBody String perfil
    ) {
        Usuario usuario = usuarioService.alterarPerfil(id, perfil);
        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));
    }

    @PatchMapping("/{id}/senha")
    @Operation(summary = "Atualiza senha do usuário")
    public ResponseEntity<UsuarioDto> alterarSenha(
            final @PathVariable UUID id,
            @Valid @RequestBody String senha
    ) {
        Usuario usuario = usuarioService.alterarSenha(id, senha);
        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui usuário")
    public ResponseEntity<Void> deletar(final @PathVariable UUID id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

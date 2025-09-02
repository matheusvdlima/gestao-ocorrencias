package io.github.matheusvdlima.incidents.service;

import io.github.matheusvdlima.incidents.dto.UsuarioShortRequestDto;
import io.github.matheusvdlima.incidents.entity.Usuario;
import io.github.matheusvdlima.incidents.enums.PerfilEnum;
import io.github.matheusvdlima.incidents.repository.UsuarioRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<Usuario> listar(Pageable pageable, String perfil, String texto) {
        Specification<Usuario> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (perfil != null && !perfil.isBlank()) {
                predicates.add(cb.equal(root.get("perfil"), PerfilEnum.valueOf(perfil)));
            }

            if (texto != null && !texto.isBlank()) {
                Predicate nome = cb.like(cb.lower(root.get("nome")), "%" + texto.toLowerCase() + "%");
                Predicate email = cb.like(cb.lower(root.get("email")), "%" + texto.toLowerCase() + "%");
                predicates.add(cb.or(nome, email));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return usuarioRepository.findAll(spec, pageable);
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional
    public Usuario criar(Usuario usuario) {
        validarEmail(usuario.getEmail());

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(UUID id, UsuarioShortRequestDto usuarioAtualizado) {
        Usuario usuario = buscarPorId(id);

        if (!usuario.getEmail().equals(usuarioAtualizado.getEmail())) {
            validarEmail(usuarioAtualizado.getEmail());
            usuario.setEmail(usuarioAtualizado.getEmail());
        }

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario alterarPerfil(UUID id, String codePerfil) {
        Usuario usuario = buscarPorId(id);
        PerfilEnum perfil = PerfilEnum.fromCode(codePerfil);
        usuario.setPerfil(perfil);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario alterarSenha(UUID id, String senha) {
        Usuario usuario = buscarPorId(id);
        usuario.setSenha(passwordEncoder.encode(senha));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletar(UUID id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }

    private void validarEmail(String email) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
        }
    }
}

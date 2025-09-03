package io.github.matheusvdlima.incidents.service;

import io.github.matheusvdlima.incidents.entity.Comentario;
import io.github.matheusvdlima.incidents.entity.Ocorrencia;
import io.github.matheusvdlima.incidents.repository.ComentarioRepository;
import io.github.matheusvdlima.incidents.repository.OcorrenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final OcorrenciaRepository ocorrenciaRepository;

    public List<Comentario> listarPorOcorrencia(UUID idOcorrencia) {
        return comentarioRepository.findByOcorrenciaIdOrderByDataCriacaoDesc(idOcorrencia);
    }

    @Transactional
    public List<Comentario> adicionarComentarios(UUID idOcorrencia, List<Comentario> comentarios) {
        Ocorrencia ocorrencia = ocorrenciaRepository.findById(idOcorrencia)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));

        List<Comentario> novosComentarios = comentarios.stream()
                .filter(c -> c.getId() == null)
                .peek(c -> c.setOcorrencia(ocorrencia))
                .toList();

        return comentarioRepository.saveAll(novosComentarios);
    }

}

package io.github.matheusvdlima.incidents.service;

import io.github.matheusvdlima.incidents.dto.EstatisticaDto;
import io.github.matheusvdlima.incidents.dto.ResultadoDto;
import io.github.matheusvdlima.incidents.entity.Ocorrencia;
import io.github.matheusvdlima.incidents.enums.PrioridadeEnum;
import io.github.matheusvdlima.incidents.enums.StatusEnum;
import io.github.matheusvdlima.incidents.repository.OcorrenciaRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;

    public Page<Ocorrencia> listar(Pageable pageable, String status, String prioridade, String texto) {
        Specification<Ocorrencia> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null && !status.isBlank()) {
                predicates.add(cb.equal(root.get("status"), StatusEnum.valueOf(status)));
            }

            if (prioridade != null && !prioridade.isBlank()) {
                predicates.add(cb.equal(root.get("prioridade"), PrioridadeEnum.valueOf(prioridade)));
            }

            if (texto != null && !texto.isBlank()) {
                Predicate titulo = cb.like(cb.lower(root.get("titulo")), "%" + texto.toLowerCase() + "%");
                Predicate descricao = cb.like(cb.lower(root.get("descricao")), "%" + texto.toLowerCase() + "%");
                predicates.add(cb.or(titulo, descricao));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return ocorrenciaRepository.findAll(spec, pageable);
    }

    public Ocorrencia buscarPorId(UUID id) {
        return ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));
    }

    @Transactional
    public Ocorrencia criar(Ocorrencia ocorrencia) {
        return ocorrenciaRepository.save(ocorrencia);
    }

    @Transactional
    public Ocorrencia atualizar(UUID id, Ocorrencia ocorrenciaAtualizada) {
        Ocorrencia ocorrencia = buscarPorId(id);
        ocorrencia.atualizar(ocorrenciaAtualizada);
        return ocorrenciaRepository.save(ocorrencia);
    }

    @Transactional
    public void deletar(UUID id) {
        Ocorrencia ocorrencia = buscarPorId(id);
        ocorrenciaRepository.delete(ocorrencia);
    }

    @Transactional
    public Ocorrencia alterarStatus(UUID id, String codeStatus) {
        Ocorrencia ocorrencia = buscarPorId(id);
        StatusEnum status = StatusEnum.fromCode(codeStatus);
        ocorrencia.setStatus(status);
        return ocorrenciaRepository.save(ocorrencia);
    }

    public EstatisticaDto obterEstatisticas() {
        Long total = ocorrenciaRepository.count();

        List<ResultadoDto> status = converterResultados(ocorrenciaRepository.contarPorStatus());
        List<ResultadoDto> prioridade = converterResultados(ocorrenciaRepository.contarPorPrioridade());

        return new EstatisticaDto(total, status, prioridade);
    }

    private List<ResultadoDto> converterResultados(List<Object[]> resultados) {
        return resultados.stream()
                .map(obj -> new ResultadoDto(
                        obj[0].toString(),
                        (Long) obj[1]
                ))
                .collect(Collectors.toList());
    }

}

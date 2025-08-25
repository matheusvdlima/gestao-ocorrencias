package io.github.matheusvdlima.incidents.repository;

import io.github.matheusvdlima.incidents.entity.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, UUID>, JpaSpecificationExecutor<Ocorrencia> {

    @Query("SELECT o.status as chave, COUNT(o) as valor FROM Ocorrencia o GROUP BY o.status")
    List<Object[]> contarPorStatus();

    @Query("SELECT o.prioridade as chave, COUNT(o) as valor FROM Ocorrencia o GROUP BY o.prioridade")
    List<Object[]> contarPorPrioridade();
}

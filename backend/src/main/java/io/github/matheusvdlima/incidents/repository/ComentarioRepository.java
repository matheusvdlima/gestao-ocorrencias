package io.github.matheusvdlima.incidents.repository;

import io.github.matheusvdlima.incidents.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ComentarioRepository extends JpaRepository<Comentario, UUID> {

    List<Comentario> findByOcorrenciaId(UUID ocorrenciaId);

    void deleteAllByOcorrenciaId(UUID ocorrenciaId);

}

package io.github.matheusvdlima.incidents.mapper;

import io.github.matheusvdlima.incidents.dto.OcorrenciaDto;
import io.github.matheusvdlima.incidents.dto.OcorrenciaRequestDto;
import io.github.matheusvdlima.incidents.entity.Ocorrencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ComentarioMapper.class)
public interface OcorrenciaMapper {

    OcorrenciaDto toDTO(Ocorrencia entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataAbertura", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    Ocorrencia toEntity(OcorrenciaRequestDto dto);
}

package io.github.matheusvdlima.incidents.mapper;

import io.github.matheusvdlima.incidents.converters.PrioridadeConverter;
import io.github.matheusvdlima.incidents.converters.StatusConverter;
import io.github.matheusvdlima.incidents.dto.OcorrenciaDto;
import io.github.matheusvdlima.incidents.dto.OcorrenciaRequestDto;
import io.github.matheusvdlima.incidents.entity.Ocorrencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ComentarioMapper.class, StatusConverter.class, PrioridadeConverter.class})
public interface OcorrenciaMapper {

    OcorrenciaDto toDTO(Ocorrencia entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataAbertura", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "toStatusEnum")
    @Mapping(target = "prioridade", source = "prioridade", qualifiedByName = "toPrioridadeEnum")
    Ocorrencia toEntity(OcorrenciaRequestDto dto);
}

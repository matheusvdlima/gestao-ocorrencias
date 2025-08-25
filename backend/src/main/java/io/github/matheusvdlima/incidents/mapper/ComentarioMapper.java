package io.github.matheusvdlima.incidents.mapper;

import io.github.matheusvdlima.incidents.dto.ComentarioDto;
import io.github.matheusvdlima.incidents.dto.ComentarioRequestDto;
import io.github.matheusvdlima.incidents.entity.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    @Mapping(source = "ocorrencia.id", target = "idOcorrencia")
    ComentarioDto toDTO(Comentario entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "ocorrencia", ignore = true)
    Comentario toEntity(ComentarioRequestDto dto);

    List<ComentarioDto> toDTOList(List<Comentario> entities);
    List<Comentario> toEntityList(List<ComentarioRequestDto> entities);
}

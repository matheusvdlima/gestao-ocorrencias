package io.github.matheusvdlima.incidents.mapper;

import io.github.matheusvdlima.incidents.dto.UsuarioDto;
import io.github.matheusvdlima.incidents.dto.UsuarioRequestDto;
import io.github.matheusvdlima.incidents.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDto toDTO(Usuario entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    Usuario toEntity(UsuarioRequestDto dto);
}

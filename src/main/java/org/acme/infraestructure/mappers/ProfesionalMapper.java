package org.acme.infraestructure.mappers;

import org.acme.domain.model.entities.Profesional;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.infraestructure.dtos.ProfesionalDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface ProfesionalMapper {

    ProfesionalMapper INSTANCE = Mappers.getMapper(ProfesionalMapper.class);

    ProfesionalDto toDto(Profesional entity);

    Profesional toEntity(ProfesionalDto dto);
}

package org.acme.infraestructure.mappers;

import org.acme.domain.model.entities.Profesional;
import org.acme.infraestructure.dtos.ProfesionalDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta")
public interface ProfesionalEntityDtoMapper {

    ProfesionalEntityDtoMapper INSTANCE = Mappers.getMapper(ProfesionalEntityDtoMapper.class);

    ProfesionalDto toDto(Profesional entity);

    Profesional toEntity(ProfesionalDto dto);
}

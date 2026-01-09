package org.acme.application.mappers;

import org.acme.infraestructure.dtos.ProfesionalDto;
import org.acme.interfaces.resources.requests.ProfesionalSchemaRequest;
import org.acme.interfaces.resources.responses.ProfesionalSchemaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta")
public interface ProfesionalMapper {

    ProfesionalMapper INSTANCE = Mappers.getMapper(ProfesionalMapper.class);

    @Mapping(source = "id", target = "id")
    ProfesionalSchemaResponse toResponse(ProfesionalDto dto);

    ProfesionalDto fromRequest(ProfesionalSchemaRequest src);

}


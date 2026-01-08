package org.acme.application.mappers;

import org.acme.infraestructure.dtos.ProfesionalDto;
import org.acme.reservas.api.beans.ProfesionalSchemaRequest;
import org.acme.reservas.api.beans.ProfesionalSchemaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface ProfesionalMapper {

    ProfesionalMapper INSTANCE = Mappers.getMapper(ProfesionalMapper.class);

    // DTO -> SchemaResponse
    @Mapping(source = "id", target = "id")
    ProfesionalSchemaResponse toResponse(ProfesionalDto dto);

    // SchemaRequest -> DTO
    ProfesionalDto fromRequest(ProfesionalSchemaRequest src);

}


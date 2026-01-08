package org.acme.application.mappers;

import org.acme.infraestructure.dtos.ClienteDto;
import org.acme.reservas.api.beans.ClienteSchemaRequest;
import org.acme.reservas.api.beans.ClienteSchemaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    // DTO -> SchemaResponse
    @Mapping(source = "id", target = "id")
    ClienteSchemaResponse toResponse(ClienteDto dto);

    // SchemaRequest -> DTO
    ClienteDto fromRequest(ClienteSchemaRequest src);
}

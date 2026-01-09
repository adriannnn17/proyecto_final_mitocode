package org.acme.application.mappers;

import org.acme.infraestructure.dtos.ClienteDto;
import org.acme.interfaces.resources.requests.ClienteSchemaRequest;
import org.acme.interfaces.resources.responses.ClienteSchemaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(source = "id", target = "id")
    ClienteSchemaResponse toResponse(ClienteDto dto);

    ClienteDto fromRequest(ClienteSchemaRequest src);
}

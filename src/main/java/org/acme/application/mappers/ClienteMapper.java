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

    @Mapping(source = "id", target = "id")
    ClienteSchemaResponse toResponse(ClienteDto dto);

    ClienteDto fromRequest(ClienteSchemaRequest src);
}

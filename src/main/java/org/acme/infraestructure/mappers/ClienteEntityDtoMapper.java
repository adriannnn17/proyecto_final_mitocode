package org.acme.infraestructure.mappers;

import org.acme.domain.model.entities.Cliente;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.infraestructure.dtos.ClienteDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta")
public interface ClienteEntityDtoMapper {

    ClienteEntityDtoMapper INSTANCE = Mappers.getMapper(ClienteEntityDtoMapper.class);

    ClienteDto toDto(Cliente entity);

    Cliente toEntity(ClienteDto dto);
}

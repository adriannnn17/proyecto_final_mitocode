package org.acme.infraestructure.mappers;

import org.acme.domain.model.entities.Cliente;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.infraestructure.dtos.ClienteDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteDto toDto(Cliente entity);

    Cliente toEntity(ClienteDto dto);
}

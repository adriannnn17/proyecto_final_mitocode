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

    // MapStruct will use these methods for conversion between EstadoActivoEnum and Boolean
    default Boolean estadoActivoEnumToBoolean(EstadoActivoEnum e) {
        if (e == null) return null;
        return e == EstadoActivoEnum.ACTIVO;
    }

    default EstadoActivoEnum booleanToEstadoActivoEnum(Boolean b) {
        if (b == null) return null;
        return b ? EstadoActivoEnum.ACTIVO : EstadoActivoEnum.INACTIVO;
    }
}

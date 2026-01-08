package org.acme.infraestructure.mappers;

import org.acme.domain.model.entities.Cliente;
import org.acme.domain.model.entities.Profesional;
import org.acme.domain.model.entities.Reserva;
import org.acme.domain.model.enums.EstadoReservaEnum;
import org.acme.infraestructure.dtos.ReservaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "cdi")
public interface ReservaMapper {

    ReservaMapper INSTANCE = Mappers.getMapper(ReservaMapper.class);

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "profesional.id", target = "profesionalId")
    @Mapping(source = "estado", target = "estado", qualifiedByName = "estadoToString")
    ReservaDto toDto(Reserva entity);

    @Mapping(source = "clienteId", target = "cliente", qualifiedByName = "idToCliente")
    @Mapping(source = "profesionalId", target = "profesional", qualifiedByName = "idToProfesional")
    @Mapping(source = "estado", target = "estado", qualifiedByName = "stringToEstado")
    Reserva toEntity(ReservaDto dto);

    @Named("idToCliente")
    default Cliente idToCliente(UUID id) {
        if (id == null) return null;
        Cliente c = new Cliente();
        c.setId(id);
        return c;
    }

    @Named("idToProfesional")
    default Profesional idToProfesional(UUID id) {
        if (id == null) return null;
        Profesional p = new Profesional();
        p.setId(id);
        return p;
    }

    @Named("estadoToString")
    default String estadoToString(EstadoReservaEnum e) {
        return e == null ? null : e.name();
    }

    @Named("stringToEstado")
    default EstadoReservaEnum stringToEstado(String s) {
        return s == null ? null : EstadoReservaEnum.valueOf(s);
    }
}


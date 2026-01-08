package org.acme.infraestructure.mappers;

import org.acme.domain.model.entities.HorarioDisponible;
import org.acme.domain.model.entities.Profesional;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "cdi")
public interface HorarioDisponibleMapper {

    HorarioDisponibleMapper INSTANCE = Mappers.getMapper(HorarioDisponibleMapper.class);

    @Mapping(source = "profesional.id", target = "profesionalId")
    @Mapping(source = "estado", target = "estado", qualifiedByName = "estadoToString")
    HorarioDisponibleDto toDto(HorarioDisponible entity);

    @Mapping(source = "profesionalId", target = "profesional", qualifiedByName = "idToProfesional")
    @Mapping(source = "estado", target = "estado", qualifiedByName = "stringToEstado")
    HorarioDisponible toEntity(HorarioDisponibleDto dto);

    @Named("idToProfesional")
    default Profesional idToProfesional(UUID id) {
        if (id == null) return null;
        Profesional p = new Profesional();
        p.setId(id);
        return p;
    }

    @Named("estadoToString")
    default String estadoToString(org.acme.domain.model.enums.EstadoActivoEnum e) {
        return e == null ? null : e.name();
    }

    @Named("stringToEstado")
    default org.acme.domain.model.enums.EstadoActivoEnum stringToEstado(String s) {
        return s == null ? null : org.acme.domain.model.enums.EstadoActivoEnum.valueOf(s);
    }
}


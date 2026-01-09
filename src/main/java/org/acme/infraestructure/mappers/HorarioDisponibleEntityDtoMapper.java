package org.acme.infraestructure.mappers;

import org.acme.domain.model.entities.HorarioDisponible;
import org.acme.domain.model.entities.Profesional;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "jakarta")
public interface HorarioDisponibleEntityDtoMapper {

    HorarioDisponibleEntityDtoMapper INSTANCE = Mappers.getMapper(HorarioDisponibleEntityDtoMapper.class);

    @Mapping(source = "profesional.id", target = "profesionalId")
    HorarioDisponibleDto toDto(HorarioDisponible entity);

    @Mapping(source = "profesionalId", target = "profesional", qualifiedByName = "idToProfesional")
    @Mapping(target = "estado", ignore = true)
    HorarioDisponible toEntity(HorarioDisponibleDto dto);

    @Named("idToProfesional")
    default Profesional idToProfesional(UUID id) {
        if (id == null) return null;
        return Profesional.builder()
                .id(id)
                .build();
    }

}


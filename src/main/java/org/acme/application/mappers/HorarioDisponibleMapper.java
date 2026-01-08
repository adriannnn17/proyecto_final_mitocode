package org.acme.application.mappers;

import org.acme.infraestructure.dtos.HorarioDisponibleDto;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaRequest;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi", uses = {org.acme.application.utils.MappingUtils.class})
public interface HorarioDisponibleMapper {

    HorarioDisponibleMapper INSTANCE = Mappers.getMapper(HorarioDisponibleMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "profesionalId", target = "profesionalId", qualifiedByName = "uuidToString")
    @Mapping(source = "fecha", target = "fecha", qualifiedByName = "localDateToString")
    @Mapping(source = "horaInicio", target = "horaInicio", qualifiedByName = "localTimeToDate")
    @Mapping(source = "horaFin", target = "horaFin", qualifiedByName = "localTimeToDate")
    HorarioDisponibleSchemaResponse toResponse(HorarioDisponibleDto dto);

    @Mapping(source = "profesionalId", target = "profesionalId", qualifiedByName = "stringToUuid")
    @Mapping(source = "fecha", target = "fecha", qualifiedByName = "stringToLocalDate")
    @Mapping(source = "horaInicio", target = "horaInicio", qualifiedByName = "dateToLocalTime")
    @Mapping(source = "horaFin", target = "horaFin", qualifiedByName = "dateToLocalTime")
    HorarioDisponibleDto fromRequest(HorarioDisponibleSchemaRequest src);

}

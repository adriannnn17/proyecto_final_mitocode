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

    // DTO -> SchemaResponse
    @Mapping(source = "id", target = "id")
    @Mapping(source = "profesionalId", target = "profesionalId", qualifiedByName = "uuidToString")
    @Mapping(source = "fecha", target = "fecha", qualifiedByName = "localDateToString")
    @Mapping(source = "horaInicio", target = "horaInicio", qualifiedByName = "localTimeToDate")
    @Mapping(source = "horaFin", target = "horaFin", qualifiedByName = "localTimeToDate")
    @Mapping(target = "estado", expression = "java(dto.getEstado() == null ? null : Boolean.valueOf(dto.getEstado()))")
    HorarioDisponibleSchemaResponse toResponse(HorarioDisponibleDto dto);

    // SchemaRequest -> DTO
    @Mapping(source = "profesionalId", target = "profesionalId", qualifiedByName = "stringToUuid")
    @Mapping(source = "fecha", target = "fecha", qualifiedByName = "stringToLocalDate")
    @Mapping(source = "horaInicio", target = "horaInicio", qualifiedByName = "dateToLocalTime")
    @Mapping(source = "horaFin", target = "horaFin", qualifiedByName = "dateToLocalTime")
    @Mapping(target = "estado", expression = "java(src.getEstado() == null ? null : String.valueOf(src.getEstado()))")
    HorarioDisponibleDto fromRequest(HorarioDisponibleSchemaRequest src);

}

package org.acme.application.mappers;

import org.acme.application.utils.MappingUtils;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;
import org.acme.interfaces.resources.requests.HorarioDisponibleSchemaRequest;
import org.acme.interfaces.resources.responses.HorarioDisponibleSchemaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta", uses = {MappingUtils.class})
public interface HorarioDisponibleMapper {

    HorarioDisponibleMapper INSTANCE = Mappers.getMapper(HorarioDisponibleMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "fecha", target = "fecha", qualifiedByName = "localDateToString")
    @Mapping(source = "horaInicio", target = "horaInicio", qualifiedByName = "localTimeToString")
    @Mapping(source = "horaFin", target = "horaFin", qualifiedByName = "localTimeToString")
    HorarioDisponibleSchemaResponse toResponse(HorarioDisponibleDto dto);

    @Mapping(source = "profesionalId", target = "profesionalId", qualifiedByName = "stringToUuid")
    @Mapping(source = "fecha", target = "fecha", qualifiedByName = "stringToLocalDate")
    @Mapping(source = "horaInicio", target = "horaInicio", qualifiedByName = "stringToLocalTime")
    @Mapping(source = "horaFin", target = "horaFin", qualifiedByName = "stringToLocalTime")
    HorarioDisponibleDto fromRequest(HorarioDisponibleSchemaRequest src);

}

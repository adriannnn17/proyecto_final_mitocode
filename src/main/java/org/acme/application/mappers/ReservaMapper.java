package org.acme.application.mappers;

import org.acme.application.utils.MappingUtils;
import org.acme.infraestructure.dtos.ReservaDto;
import org.acme.interfaces.resources.requests.RegistroReservaSchemaRequest;
import org.acme.interfaces.resources.responses.RegistroReservaSchemaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "jakarta",
        uses = {MappingUtils.class, ClienteMapper.class, ProfesionalMapper.class})

public interface ReservaMapper {

    ReservaMapper INSTANCE = Mappers.getMapper(ReservaMapper.class);

    @Mapping(source = "fecha", target = "fecha", qualifiedByName = "localDateToString")
    @Mapping(source = "horaInicio", target = "horaInicio", qualifiedByName = "localTimeToDate")
    @Mapping(source = "horaFin", target = "horaFin", qualifiedByName = "localTimeToDate")
    @Mapping(source = "estado", target = "estado", qualifiedByName = "stringToRegistroReservaResponseEstado")
    RegistroReservaSchemaResponse toResponse(ReservaDto dto);

    @Mapping(source = "clienteId", target = "clienteId")
    @Mapping(source = "profesionalId", target = "profesionalId")
    @Mapping(source = "fecha", target = "fecha", qualifiedByName = "stringToLocalDate")
    @Mapping(source = "horaInicio", target = "horaInicio", qualifiedByName = "dateToLocalTime")
    @Mapping(source = "horaFin", target = "horaFin", qualifiedByName = "dateToLocalTime")
    ReservaDto fromRequest(RegistroReservaSchemaRequest src);

}

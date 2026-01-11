package org.acme.application.utils;

import lombok.experimental.UtilityClass;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;
import org.acme.infraestructure.dtos.ReservaDto;
import org.acme.interfaces.resources.responses.RegistroReservaSchemaResponse;
import org.acme.interfaces.resources.responses.ReservaSegunProfesionalSchemaResponse;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@UtilityClass
public class MappingUtils {

    @Named("stringToLocalDate")
    public static LocalDate stringToLocalDate(String s) {
        if (s == null) return null;
        return LocalDate.parse(s);
    }

    @Named("localDateToString")
    public static String localDateToString(LocalDate d) {
        if (d == null) return null;
        return d.toString();
    }

    @Named("localTimeToString")
    public static String localTimeToString(LocalTime d) {
        if (d == null) return null;
        return d.toString();
    }

    @Named("stringToLocalTime")
    public static LocalTime stringToLocalTime(String s) {
        if (s == null) return null;
        return LocalTime.parse(s);
    }

    @Named("stringToUuid")
    public static UUID stringToUuid(String s) {
        if (s == null) return null;
        return UUID.fromString(s);
    }

    @Named("uuidToString")
    public static String uuidToString(UUID id) {
        if (id == null) return null;
        return id.toString();
    }

    @Named("enumToString")
    public static String enumToString(Enum<?> e) {
        if (e == null) return null;
        return e.toString();
    }

    @Named("stringToRegistroReservaResponseEstado")
    public static RegistroReservaSchemaResponse.Estado stringToRegistroReservaResponseEstado(String s) {
        if (s == null) return null;
        return RegistroReservaSchemaResponse.Estado.fromValue(s);
    }

    public static HorarioDisponibleDto mapReservaToHorarioDisponibleHours(ReservaDto reservaDto) {
        return HorarioDisponibleDto.builder()
                .profesionalId(reservaDto.getProfesionalId())
                .fecha(reservaDto.getFecha())
                .horaInicio(reservaDto.getHoraInicio())
                .horaFin(reservaDto.getHoraFin())
                .build();
    }

    public static Boolean EstadoActivoEnumToBoolean(EstadoActivoEnum estado) {
        if (estado == null) return null;
        return estado == EstadoActivoEnum.ACTIVO;
    }

    public static Map<String, List<ReservaSegunProfesionalSchemaResponse>> buildProfessionalsSummary(List<ReservaDto> reservaDtos) {
        AtomicInteger counter = new AtomicInteger(1);

        return reservaDtos.stream()
                .sorted(Comparator.comparing(ReservaDto::getFecha, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(ReservaDto::getHoraInicio, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.groupingBy(r -> r.getFecha().toString(),
                        LinkedHashMap::new,
                        Collectors.mapping(r -> {
                            String nc = r.getCliente().getNombres();
                            String ac = r.getCliente().getApellidos();
                            String cl = (nc + " " + ac).trim();

                            String np = r.getProfesional().getNombres() != null ? r.getProfesional().getNombres() : "";
                            String ap = r.getProfesional().getApellidos() != null ? r.getProfesional().getApellidos() : "";
                            String pr = (np + " " + ap).trim();

                            ReservaSegunProfesionalSchemaResponse reservaSegunProfesionalSchemaResponse = new ReservaSegunProfesionalSchemaResponse();
                            reservaSegunProfesionalSchemaResponse.setNumeroReserva("Reserva " + counter.get());
                            reservaSegunProfesionalSchemaResponse.setCliente(cl);
                            reservaSegunProfesionalSchemaResponse.setProfesional(pr);

                            counter.getAndIncrement();

                            return reservaSegunProfesionalSchemaResponse;
                        }, Collectors.toList())));
    }
}

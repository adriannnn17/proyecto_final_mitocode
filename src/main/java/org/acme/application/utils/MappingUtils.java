package org.acme.application.utils;

import lombok.experimental.UtilityClass;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;
import org.acme.infraestructure.dtos.ReservaDto;
import org.acme.interfaces.resources.responses.RegistroReservaSchemaResponse;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

    @Named("dateToLocalTime")
    public static LocalTime dateToLocalTime(Date d) {
        if (d == null) return null;
        return Instant.ofEpochMilli(d.getTime()).atZone(ZoneOffset.UTC).toLocalTime();
    }

    @Named("localTimeToDate")
    public static Date localTimeToDate(LocalTime t) {
        if (t == null) return null;
        Instant instant = t.atDate(LocalDate.of(1970, 1, 1)).atZone(ZoneOffset.UTC).toInstant();
        return Date.from(instant);
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

    // Nuevo método: construye el resumen de profesionales a partir de la lista de reservas
    public static List<Map<String, Object>> buildProfessionalsSummary(List<ReservaDto> reservaDtos) {
        if (reservaDtos == null) return java.util.Collections.emptyList();

        // Filtrar reservas cuyo estado sea distinto de "CANCELADA"
        List<ReservaDto> filtered = reservaDtos.stream()
                .filter(r -> r.getEstado() == null || !"CANCELADA".equalsIgnoreCase(r.getEstado()))
                .collect(Collectors.toList());

        // Agrupar por profesional (nombre completo si está disponible, sino id)
        Map<String, List<ReservaDto>> byProfessional = filtered.stream()
                .collect(Collectors.groupingBy(r -> {
                    if (r.getProfesional() != null && r.getProfesional().getNombres() != null) {
                        return r.getProfesional().getNombres() + " " + r.getProfesional().getApellidos();
                    }
                    return r.getProfesionalId() != null ? r.getProfesionalId().toString() : "UNKNOWN";
                }));

        // Construir lista ordenada de profesionales con conteo y mapa de fechas -> reservas
        List<Map<String, Object>> professionals = byProfessional.entrySet().stream()
                .map(entry -> {
                    String prof = entry.getKey();
                    List<ReservaDto> list = entry.getValue();
                    long count = list.size();

                    // Map<LocalDate, List<String>> donde cada reserva se representa por su fecha (identificador solicitado)
                    Map<LocalDate, List<String>> datesMap = list.stream()
                            .collect(Collectors.groupingBy(ReservaDto::getFecha,
                                    Collectors.mapping(r -> r.getFecha().toString(), Collectors.toList())));

                    Map<String, Object> profInfo = new java.util.LinkedHashMap<>();
                    profInfo.put("professional", prof);
                    profInfo.put("count", count);
                    profInfo.put("dates", datesMap);
                    return profInfo;
                })
                .sorted(java.util.Comparator.comparingLong((Map<String, Object> m) -> (Long) m.get("count")).reversed())
                .collect(Collectors.toList());

        return professionals;
    }
}

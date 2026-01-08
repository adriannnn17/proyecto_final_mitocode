package org.acme.application.utils;

import lombok.experimental.UtilityClass;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

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

    @Named("stringToRegistroReservaRequestEstado")
    public static org.acme.reservas.api.beans.RegistroReservaSchemaRequest.Estado stringToRegistroReservaRequestEstado(String s) {
        if (s == null) return null;
        return org.acme.reservas.api.beans.RegistroReservaSchemaRequest.Estado.fromValue(s);
    }

    @Named("stringToRegistroReservaResponseEstado")
    public static org.acme.reservas.api.beans.RegistroReservaSchemaResponse.Estado stringToRegistroReservaResponseEstado(String s) {
        if (s == null) return null;
        return org.acme.reservas.api.beans.RegistroReservaSchemaResponse.Estado.fromValue(s);
    }

    @Named("clienteIdToClienteSchemaResponse")
    public static org.acme.reservas.api.beans.ClienteSchemaResponse clienteIdToClienteSchemaResponse(java.util.UUID id) {
        if (id == null) return null;
        org.acme.reservas.api.beans.ClienteSchemaResponse c = new org.acme.reservas.api.beans.ClienteSchemaResponse();
        c.setId(id);
        return c;
    }

    @Named("profesionalIdToProfesionalSchemaResponse")
    public static org.acme.reservas.api.beans.ProfesionalSchemaResponse profesionalIdToProfesionalSchemaResponse(java.util.UUID id) {
        if (id == null) return null;
        org.acme.reservas.api.beans.ProfesionalSchemaResponse p = new org.acme.reservas.api.beans.ProfesionalSchemaResponse();
        p.setId(id);
        return p;
    }

}

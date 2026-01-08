package org.acme.domain.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;

import java.util.UUID;

public interface HorarioDisponibleService {

    Multi<HorarioDisponibleDto> listAvailabilityHours();

    Uni<HorarioDisponibleDto> findAvailabilityHour(UUID id);

    Uni<Void> createAvailabilityHour(HorarioDisponibleDto horario);

    Uni<Void> updateAvailabilityHour(HorarioDisponibleDto horario, UUID id);

    Uni<Void> deleteAvailabilityHour(UUID id);
}

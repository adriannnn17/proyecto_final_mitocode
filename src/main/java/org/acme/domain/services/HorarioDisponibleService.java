package org.acme.domain.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;

import java.util.UUID;

public interface HorarioDisponibleService {

    Multi<HorarioDisponibleDto> listAvailableHours();

    Uni<HorarioDisponibleDto> findAvailableHour(UUID id);

    Uni<Void> createAvailableHour(HorarioDisponibleDto horario);

    Uni<Void> updateAvailableHour(HorarioDisponibleDto horario, UUID id);

    Uni<Void> deleteAvailableHour(UUID id);
}

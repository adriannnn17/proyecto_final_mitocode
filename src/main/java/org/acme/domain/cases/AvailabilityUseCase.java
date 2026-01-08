package org.acme.domain.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaRequest;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaResponse;

public interface AvailabilityUseCase {

    Multi<HorarioDisponibleSchemaResponse> listAvailableHours();

    Uni<Void> createAvailableHour(HorarioDisponibleSchemaRequest data);

    Uni<HorarioDisponibleSchemaResponse> findAvailableHour(String availableHoursId);

    Uni<Void> updateAvailableHour(String availableHoursId, HorarioDisponibleSchemaRequest data);

    Uni<Void> deleteAvailableHour(String availableHoursId);
}


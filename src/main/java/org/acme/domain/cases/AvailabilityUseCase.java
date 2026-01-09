package org.acme.domain.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.interfaces.resources.requests.HorarioDisponibleSchemaRequest;
import org.acme.interfaces.resources.responses.HorarioDisponibleSchemaResponse;

public interface AvailabilityUseCase {

    Multi<HorarioDisponibleSchemaResponse> listAvailabilityHours();

    Uni<Void> createAvailabilityHour(HorarioDisponibleSchemaRequest data);

    Uni<HorarioDisponibleSchemaResponse> findAvailabilityHour(String availabilityHoursId);

    Uni<Void> updateAvailabilityHour(String availabilityHoursId, HorarioDisponibleSchemaRequest data);

    Uni<Void> deleteAvailabilityHour(String availabilityHoursId);
}


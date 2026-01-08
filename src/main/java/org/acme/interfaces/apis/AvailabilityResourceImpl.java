package org.acme.interfaces.apis;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.acme.domain.cases.AvailabilityUseCase;
import org.acme.reservas.api.AvailabilityResource;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaRequest;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaResponse;

import java.util.List;
import java.util.concurrent.CompletionStage;

public class AvailabilityResourceImpl implements AvailabilityResource {

    @Inject
    AvailabilityUseCase availabilityUseCase;

    @Override
    public CompletionStage<List<HorarioDisponibleSchemaResponse>> listAvailabilityHour() {
        return availabilityUseCase.listAvailabilityHours()
                .collect()
                .asList()
                .subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> createAvailabilityHour(@Valid HorarioDisponibleSchemaRequest data) {
        return availabilityUseCase.createAvailabilityHour(data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<HorarioDisponibleSchemaResponse> findAvailabilityHour(@Valid String availabilityHoursId) {
        return availabilityUseCase.findAvailabilityHour(availabilityHoursId).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> updateAvailabilityHour(@Valid String availabilityHoursId,
                                                     @Valid HorarioDisponibleSchemaRequest data) {
        return availabilityUseCase.updateAvailabilityHour(availabilityHoursId, data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> deleteAvailabilityHour(@Valid String availabilityHoursId) {
        return availabilityUseCase.deleteAvailabilityHour(availabilityHoursId).subscribeAsCompletionStage();
    }
}

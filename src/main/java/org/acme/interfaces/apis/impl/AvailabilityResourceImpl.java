package org.acme.interfaces.apis.impl;

import jakarta.inject.Inject;
import org.acme.domain.cases.AvailabilityUseCase;
import org.acme.interfaces.apis.AvailabilityResource;
import org.acme.interfaces.resources.requests.HorarioDisponibleSchemaRequest;
import org.acme.interfaces.resources.responses.HorarioDisponibleSchemaResponse;

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
    public CompletionStage<Void> createAvailabilityHour(HorarioDisponibleSchemaRequest data) {
        return availabilityUseCase.createAvailabilityHour(data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<HorarioDisponibleSchemaResponse> findAvailabilityHour(String availabilityHoursId) {
        return availabilityUseCase.findAvailabilityHour(availabilityHoursId).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> updateAvailabilityHour(String availabilityHoursId,
                                                     HorarioDisponibleSchemaRequest data) {
        return availabilityUseCase.updateAvailabilityHour(availabilityHoursId, data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> deleteAvailabilityHour(String availabilityHoursId) {
        return availabilityUseCase.deleteAvailabilityHour(availabilityHoursId).subscribeAsCompletionStage();
    }
}

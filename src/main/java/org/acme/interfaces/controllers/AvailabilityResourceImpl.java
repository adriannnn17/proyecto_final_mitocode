package org.acme.interfaces.controllers;

import jakarta.inject.Inject;
import org.acme.domain.cases.AvailabilityUseCase;
import org.acme.reservas.api.AvailableResource;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaRequest;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaResponse;

import java.util.List;
import java.util.concurrent.CompletionStage;

public class AvailabilityResourceImpl implements AvailableResource {

    @Inject
    AvailabilityUseCase availabilityUseCase;

    @Override
    public CompletionStage<List<HorarioDisponibleSchemaResponse>> listAvailableHour() {
        return availabilityUseCase.listAvailableHours()
                .collect()
                .asList()
                .subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> createAvailableHour(HorarioDisponibleSchemaRequest data) {
        return availabilityUseCase.createAvailableHour(data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<HorarioDisponibleSchemaResponse> findAvailableHour(String availableHoursId) {
        return availabilityUseCase.findAvailableHour(availableHoursId).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> updateAvailableHour(String availableHoursId, HorarioDisponibleSchemaRequest data) {
        return availabilityUseCase.updateAvailableHour(availableHoursId, data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> deleteAvailableHour(String availableHoursId) {
        return availabilityUseCase.deleteAvailableHour(availableHoursId).subscribeAsCompletionStage();
    }
}

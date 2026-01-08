package org.acme.interfaces.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.mappers.HorarioDisponibleMapper;
import org.acme.domain.cases.AvailabilityUseCase;
import org.acme.domain.services.HorarioDisponibleService;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaRequest;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaResponse;

import java.util.UUID;

@ApplicationScoped
public class AvailabilityUseCaseImpl implements AvailabilityUseCase {

    @Inject
    HorarioDisponibleService horarioService;

    @Override
    public Multi<HorarioDisponibleSchemaResponse> listAvailableHours() {
        return horarioService.listAvailableHours()
                .map(HorarioDisponibleMapper.INSTANCE::toResponse);
    }

    @Override
    public Uni<Void> createAvailableHour(HorarioDisponibleSchemaRequest data) {
        return horarioService.createAvailableHour(HorarioDisponibleMapper.INSTANCE.fromRequest(data));
    }

    @Override
    public Uni<HorarioDisponibleSchemaResponse> findAvailableHour(String availableHoursId) {
        return horarioService.findAvailableHour(UUID.fromString(availableHoursId))
                .map(HorarioDisponibleMapper.INSTANCE::toResponse);
    }

    @Override
    public Uni<Void> updateAvailableHour(String availableHoursId, HorarioDisponibleSchemaRequest data) {
        return horarioService.updateAvailableHour(HorarioDisponibleMapper.INSTANCE.fromRequest(data),
                UUID.fromString(availableHoursId));
    }

    @Override
    public Uni<Void> deleteAvailableHour(String availableHoursId) {
        return horarioService.deleteAvailableHour(UUID.fromString(availableHoursId));
    }
}

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
    public Multi<HorarioDisponibleSchemaResponse> listAvailabilityHours() {
        return horarioService.listAvailabilityHours()
                .map(HorarioDisponibleMapper.INSTANCE::toResponse);
    }

    @Override
    public Uni<Void> createAvailabilityHour(HorarioDisponibleSchemaRequest data) {
        return horarioService.createAvailabilityHour(HorarioDisponibleMapper.INSTANCE.fromRequest(data));
    }

    @Override
    public Uni<HorarioDisponibleSchemaResponse> findAvailabilityHour(String availabilityHoursId) {
        return horarioService.findAvailabilityHour(UUID.fromString(availabilityHoursId))
                .map(HorarioDisponibleMapper.INSTANCE::toResponse);
    }

    @Override
    public Uni<Void> updateAvailabilityHour(String availabilityHoursId, HorarioDisponibleSchemaRequest data) {
        return horarioService.updateAvailabilityHour(HorarioDisponibleMapper.INSTANCE.fromRequest(data),
                UUID.fromString(availabilityHoursId));
    }

    @Override
    public Uni<Void> deleteAvailabilityHour(String availabilityHoursId) {
        return horarioService.deleteAvailabilityHour(UUID.fromString(availabilityHoursId));
    }
}

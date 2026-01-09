package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.repository.HorarioDisponibleRepository;
import org.acme.domain.services.HorarioDisponibleService;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;
import org.acme.infraestructure.mappers.HorarioDisponibleEntityDtoMapper;

import java.util.UUID;

@ApplicationScoped
public class HorarioDisponibleServiceImpl implements HorarioDisponibleService {

    @Inject
    HorarioDisponibleRepository horarioDisponibleRepository;


    @Override
    public Multi<HorarioDisponibleDto> listAvailabilityHours() {
        return horarioDisponibleRepository.findAllInactivos().map(HorarioDisponibleEntityDtoMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<HorarioDisponibleDto> findAvailabilityHour(UUID id) {
        return horarioDisponibleRepository.findByIdAndInactivo(id).map(HorarioDisponibleEntityDtoMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<Void> createAvailabilityHour(HorarioDisponibleDto horario) {
        return horarioDisponibleRepository.saveHorarioDisponible(HorarioDisponibleEntityDtoMapper.INSTANCE.toEntity(horario));
    }

    @Override
    public Uni<Void> updateAvailabilityHour(HorarioDisponibleDto horario, UUID id) {
        return horarioDisponibleRepository.updateHorarioDisponible(HorarioDisponibleEntityDtoMapper.INSTANCE.toEntity(horario), id);
    }

    @Override
    public Uni<Void> deleteAvailabilityHour(UUID id) {
        return horarioDisponibleRepository.deleteHorarioDisponible(id);
    }
}

package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.repository.HorarioDisponibleRepository;
import org.acme.domain.services.HorarioDisponibleService;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;
import org.acme.infraestructure.mappers.HorarioDisponibleMapper;

import java.util.UUID;

@ApplicationScoped
public class HorarioDisponibleServiceImpl implements HorarioDisponibleService {

    @Inject
    HorarioDisponibleRepository horarioDisponibleRepository;


    @Override
    public Multi<HorarioDisponibleDto> listAvailableHours() {
        return horarioDisponibleRepository.findAllInactivos().map(HorarioDisponibleMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<HorarioDisponibleDto> findAvailableHour(UUID id) {
        return horarioDisponibleRepository.findByIdAndInactivo(id).map(HorarioDisponibleMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<Void> createAvailableHour(HorarioDisponibleDto horario) {
        return horarioDisponibleRepository.saveHorarioDisponible(HorarioDisponibleMapper.INSTANCE.toEntity(horario));
    }

    @Override
    public Uni<Void> updateAvailableHour(HorarioDisponibleDto horario, UUID id) {
        return horarioDisponibleRepository.updateHorarioDisponible(HorarioDisponibleMapper.INSTANCE.toEntity(horario), id);
    }

    @Override
    public Uni<Void> deleteAvailableHour(UUID id) {
        return horarioDisponibleRepository.deleteHorarioDisponible(id);
    }
}

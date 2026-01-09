package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.repository.ReservaRepository;
import org.acme.domain.services.ReservaService;
import org.acme.infraestructure.dtos.ReservaDto;
import org.acme.infraestructure.mappers.ReservaEntityDtoMapper;

import java.util.UUID;

@ApplicationScoped
public class ReservaServiceImpl implements ReservaService {

    @Inject
    ReservaRepository reservaRepository;

    @Override
    public Multi<ReservaDto> listBookings() {
        return reservaRepository.findAllByEstado().map(ReservaEntityDtoMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<ReservaDto> findBooking(UUID id) {
        return reservaRepository.findByEstado(id).map(ReservaEntityDtoMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<Void> createBooking(ReservaDto reserva) {
        return reservaRepository.saveReserva(ReservaEntityDtoMapper.INSTANCE.toEntity(reserva));
    }

    @Override
    public Uni<Void> updateBooking(ReservaDto reserva, UUID id) {
        return reservaRepository.updateReserva(ReservaEntityDtoMapper.INSTANCE.toEntity(reserva), id);
    }

    @Override
    public Uni<Void> deleteBooking(UUID id) {
        return reservaRepository.deleteReserva(id);
    }
}

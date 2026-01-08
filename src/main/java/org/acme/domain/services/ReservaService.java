package org.acme.domain.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.infraestructure.dtos.ReservaDto;

import java.util.UUID;

public interface ReservaService {

    Multi<ReservaDto> listBookings();

    Uni<ReservaDto> findBooking(UUID id);

    Uni<Void> createBooking(ReservaDto reserva);

    Uni<Void> updateBooking(ReservaDto reserva, UUID id);

    Uni<Void> deleteBooking(UUID id);
}

package org.acme.interfaces.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.mappers.ReservaMapper;
import org.acme.domain.cases.BookingsUseCase;
import org.acme.domain.services.ReservaService;
import org.acme.interfaces.requests.RegistroReservaSchemaRequest;
import org.acme.interfaces.responses.RegistroReservaSchemaResponse;
import org.acme.interfaces.responses.ReservasSegunProfesionalSchemaResponse;

import java.util.UUID;

@ApplicationScoped
public class BookingsUseCaseImpl implements BookingsUseCase {

    @Inject
    ReservaService reservaService;

    @Override
    public Uni<ReservasSegunProfesionalSchemaResponse> listProfessionalQuery() {
        return Uni.createFrom().item(ReservasSegunProfesionalSchemaResponse::new);
    }

    @Override
    public Multi<RegistroReservaSchemaResponse> listBookings(String idClient, String idProfessional, String maxDate, String minDate, String specialty) {
        return reservaService.listBookings()
                .map(ReservaMapper.INSTANCE::toResponse);
    }

    @Override
    public Uni<Void> createBookings(RegistroReservaSchemaRequest data) {
        return reservaService.createBooking(ReservaMapper.INSTANCE.fromRequest(data));
    }

    @Override
    public Uni<RegistroReservaSchemaResponse> findBookings(String idBookings) {
        return reservaService.findBooking(UUID.fromString(idBookings))
                .map(ReservaMapper.INSTANCE::toResponse);
    }

    @Override
    public Uni<Void> updateBookings(String idBookings, RegistroReservaSchemaRequest data) {
        return reservaService.updateBooking(ReservaMapper.INSTANCE.fromRequest(data),
                UUID.fromString(idBookings));
    }

    @Override
    public Uni<Void> deleteBookings(String idBookings) {
        return reservaService.deleteBooking(UUID.fromString(idBookings));
    }
}

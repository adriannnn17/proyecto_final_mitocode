package org.acme.interfaces.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.mappers.ReservaMapper;
import org.acme.domain.cases.BookingsUseCase;
import org.acme.domain.services.ReservaService;
import org.acme.infraestructure.dtos.others.ReservaGet;
import org.acme.interfaces.resources.requests.RegistroReservaSchemaRequest;
import org.acme.interfaces.resources.responses.RegistroReservaSchemaResponse;
import org.acme.interfaces.resources.responses.ReservasSegunProfesionalSchemaResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.acme.application.utils.MappingUtils.buildProfessionalsSummary;

@ApplicationScoped
public class BookingsUseCaseImpl implements BookingsUseCase {

    @Inject
    ReservaService reservaService;

    @Override
    public Uni<ReservasSegunProfesionalSchemaResponse> listProfessionalQuery() {
        return reservaService.listBookings(ReservaGet.builder()
                .build()).collect().asList().map(reservaDtos -> {

            List<Map<String, Object>> professionals = buildProfessionalsSummary(reservaDtos);

            ReservasSegunProfesionalSchemaResponse resp = new ReservasSegunProfesionalSchemaResponse();
            resp.setAdditionalProperty("professionals", professionals);
            return resp;
        });
    }

    @Override
    public Multi<RegistroReservaSchemaResponse> listBookings(String idClient, String idProfessional, String maxDate, String minDate, String specialty) {
        return reservaService.listBookings(ReservaGet.builder()
                        .idClient(idClient)
                        .idProfessional(idProfessional)
                        .maxDate(maxDate)
                        .minDate(minDate)
                        .specialty(specialty)
                        .build())
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

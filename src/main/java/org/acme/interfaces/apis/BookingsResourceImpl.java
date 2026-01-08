package org.acme.interfaces.apis;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.acme.domain.cases.BookingsUseCase;
import org.acme.reservas.api.BookingsResource;
import org.acme.reservas.api.beans.RegistroReservaSchemaRequest;
import org.acme.reservas.api.beans.RegistroReservaSchemaResponse;
import org.acme.reservas.api.beans.ReservasSegunProfesionalSchemaResponse;

import java.util.List;
import java.util.concurrent.CompletionStage;

public class BookingsResourceImpl implements BookingsResource {

    @Inject
    BookingsUseCase bookingsUseCase;

    @Override
    public CompletionStage<ReservasSegunProfesionalSchemaResponse> listProfessionalQuery() {
        return bookingsUseCase.listProfessionalQuery().subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<List<RegistroReservaSchemaResponse>> listBookings(@Valid String idClient,
                                                                             @Valid String idProfessional,
                                                                             @Valid String maxDate,
                                                                             @Valid String minDate,
                                                                             @Valid String specialty) {
        return bookingsUseCase.listBookings(idClient, idProfessional, maxDate, minDate, specialty)
                .collect()
                .asList()
                .subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> createBookings(@Valid RegistroReservaSchemaRequest data) {
        return bookingsUseCase.createBookings(data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<RegistroReservaSchemaResponse> findBookings(@Valid String idBookings) {
        return bookingsUseCase.findBookings(idBookings).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> updateBookings(@Valid String idBookings,
                                                @Valid RegistroReservaSchemaRequest data) {
        return bookingsUseCase.updateBookings(idBookings, data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> deleteBookings(@Valid String idBookings) {
        return bookingsUseCase.deleteBookings(idBookings).subscribeAsCompletionStage();
    }
}

package org.acme.interfaces.apis.impl;

import jakarta.inject.Inject;
import org.acme.domain.cases.BookingsUseCase;
import org.acme.interfaces.apis.BookingsResource;
import org.acme.interfaces.resources.requests.RegistroReservaSchemaRequest;
import org.acme.interfaces.resources.responses.RegistroReservaSchemaResponse;
import org.acme.interfaces.resources.responses.ReservasSegunProfesionalSchemaResponse;

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
    public CompletionStage<List<RegistroReservaSchemaResponse>> listBookings(String idClient,
                                                                             String idProfessional,
                                                                             String date,
                                                                             String specialty) {
        return bookingsUseCase.listBookings(idClient, idProfessional, date, specialty)
                .collect()
                .asList()
                .subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> createBookings(RegistroReservaSchemaRequest data) {
        return bookingsUseCase.createBookings(data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<RegistroReservaSchemaResponse> findBookings(String idBookings) {
        return bookingsUseCase.findBookings(idBookings).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> updateBookings(String idBookings,
                                                RegistroReservaSchemaRequest data) {
        return bookingsUseCase.updateBookings(idBookings, data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> deleteBookings(String idBookings) {
        return bookingsUseCase.deleteBookings(idBookings).subscribeAsCompletionStage();
    }
}

package org.acme.domain.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.interfaces.resources.requests.RegistroReservaSchemaRequest;
import org.acme.interfaces.resources.responses.RegistroReservaSchemaResponse;
import org.acme.interfaces.resources.responses.ReservasSegunProfesionalSchemaResponse;

public interface BookingsUseCase {

    Uni<ReservasSegunProfesionalSchemaResponse> listProfessionalQuery();

    Multi<RegistroReservaSchemaResponse> listBookings(String idClient, String idProfessional, String date, String specialty);

    Uni<Void> createBookings(RegistroReservaSchemaRequest data);

    Uni<RegistroReservaSchemaResponse> findBookings(String idBookings);

    Uni<Void> updateBookings(String idBookings, RegistroReservaSchemaRequest data);

    Uni<Void> deleteBookings(String idBookings);
}


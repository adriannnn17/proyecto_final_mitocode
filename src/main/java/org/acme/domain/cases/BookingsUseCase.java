package org.acme.domain.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.reservas.api.beans.RegistroReservaSchemaRequest;
import org.acme.reservas.api.beans.RegistroReservaSchemaResponse;
import org.acme.reservas.api.beans.ReservasSegunProfesionalSchemaResponse;

public interface BookingsUseCase {

    Uni<ReservasSegunProfesionalSchemaResponse> listProfessionalQuery();

    Multi<RegistroReservaSchemaResponse> listBookings(String idClient, String idProfessional, String maxDate, String minDate, String specialty);

    Uni<Void> createBookings(RegistroReservaSchemaRequest data);

    Uni<RegistroReservaSchemaResponse> findBookings(String idBookings);

    Uni<Void> updateBookings(String idBookings, RegistroReservaSchemaRequest data);

    Uni<Void> deleteBookings(String idBookings);
}


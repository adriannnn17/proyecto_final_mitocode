package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.entities.Reserva;
import org.acme.infraestructure.dtos.others.ReservaGet;

import java.util.UUID;

public interface ReservaRepository extends PanacheRepositoryBase<Reserva, UUID> {

    Multi<Reserva> findAllByEstado(ReservaGet reservaGet);

    Uni<Reserva> findByEstado(UUID id);

    Uni<Void> saveReserva(Reserva reserva);

    Uni<Void> updateReserva(Reserva reserva, UUID uuid);

    Uni<Void> deleteReserva(UUID uuid);

    Uni<Boolean> validateOtrasReservasProfesional(Reserva reserva);
}

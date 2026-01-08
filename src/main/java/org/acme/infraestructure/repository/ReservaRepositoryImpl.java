package org.acme.infraestructure.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.acme.domain.model.entities.Reserva;
import org.acme.domain.model.enums.EstadoReservaEnum;
import org.acme.domain.repository.ReservaRepository;

import java.util.UUID;

@ApplicationScoped
public class ReservaRepositoryImpl implements ReservaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Multi<Reserva> findAllByEstado() {
        return Multi.createFrom()
                .iterable(find("estado != ?1", EstadoReservaEnum.CANCELADA)
                        .list());

    }

    @Override
    public Uni<Reserva> findByEstado(UUID id) {

        return find("id = ?1 and estado != ?2",
                id,
                EstadoReservaEnum.CANCELADA)
                .firstResult();
    }

    @Override
    public Uni<Void> saveReserva(Reserva reserva) {
        return Uni.createFrom().item(() -> {
            entityManager.persist(reserva);
            return null;
        });
    }

    @Override
    public Uni<Void> updateReserva(Reserva reserva, UUID uuid) {
        return Uni.createFrom().item(() -> {
            reserva.setId(uuid);
            entityManager.merge(reserva);
            return null;
        });
    }

    @Override
    public Uni<Void> deleteReserva(UUID uuid) {
        return Uni.createFrom().item(() -> {
            Reserva managed = entityManager.find(Reserva.class, uuid);
            if (managed != null) {
                entityManager.remove(managed);
            }
            return null;
        });
    }
}

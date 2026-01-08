package org.acme.infraestructure.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.acme.domain.model.entities.Reserva;
import org.acme.domain.model.enums.EstadoReservaEnum;
import org.acme.domain.repository.ReservaRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReservaRepositoryImpl implements ReservaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Multi<Reserva> findAllByEstado() {
        return Uni.createFrom().item(this::findAllByEstadoBlocking)
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transformToMulti(list -> Multi.createFrom().iterable(list));
    }

    @Transactional
    protected List<Reserva> findAllByEstadoBlocking() {
        return find("estado != ?1", EstadoReservaEnum.CANCELADA).list();
    }

    @Override
    public Uni<Reserva> findByEstado(UUID id) {
        return Uni.createFrom().item(() -> findByEstadoBlocking(id))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected Reserva findByEstadoBlocking(UUID id) {
        return (Reserva) find("id = ?1 and estado != ?2", id, EstadoReservaEnum.CANCELADA).firstResult();
    }

    @Override
    public Uni<Void> saveReserva(Reserva reserva) {
        return Uni.createFrom().item(() -> {
            saveReservaBlocking(reserva);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void saveReservaBlocking(Reserva reserva) {
        reserva.setEstado(EstadoReservaEnum.CREADA);
        entityManager.persist(reserva);
    }

    @Override
    public Uni<Void> updateReserva(Reserva reserva, UUID uuid) {
        return Uni.createFrom().item(() -> {
            updateReservaBlocking(reserva, uuid);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void updateReservaBlocking(Reserva reserva, UUID uuid) {
        reserva.setEstado(EstadoReservaEnum.CREADA);
        reserva.setId(uuid);
        entityManager.merge(reserva);
    }

    @Override
    public Uni<Void> deleteReserva(UUID uuid) {
        return Uni.createFrom().item(() -> {
            deleteReservaBlocking(uuid);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void deleteReservaBlocking(UUID uuid) {
        Reserva managed = entityManager.find(Reserva.class, uuid);
        if (managed != null) {
            managed.setEstado(EstadoReservaEnum.CANCELADA);
            entityManager.merge(managed);
        }
    }
}

package org.acme.infraestructure.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.acme.domain.model.entities.Reserva;
import org.acme.domain.model.enums.EstadoReservaEnum;
import org.acme.domain.repository.ReservaRepository;
import org.acme.infraestructure.dtos.others.ReservaGet;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.acme.application.utils.MappingUtils.localTimeToString;
import static org.acme.application.utils.MappingUtils.stringToLocalDate;

@ApplicationScoped
public class ReservaRepositoryImpl implements ReservaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Multi<Reserva> findAllByEstado(ReservaGet reservaGet) {
        return Uni.createFrom().item(() -> this.findAllByEstadoBlocking(reservaGet))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transformToMulti(list -> Multi.createFrom().iterable(list));
    }

    @Transactional
    protected List<Reserva> findAllByEstadoBlocking(ReservaGet reservaGet) {

        StringBuilder jpql = new StringBuilder("SELECT r FROM Reserva r WHERE r.estado = :estado");
        Map<String, Object> params = new HashMap<>();

        if (reservaGet != null) {
            if (reservaGet.getIdClient() != null && !reservaGet.getIdClient().isBlank()) {
                UUID clientId = UUID.fromString(reservaGet.getIdClient());
                jpql.append(" AND r.cliente.id = :clientId");
                params.put("clientId", clientId);
            }

            if (reservaGet.getIdProfessional() != null && !reservaGet.getIdProfessional().isBlank()) {
                UUID profId = UUID.fromString(reservaGet.getIdProfessional());
                jpql.append(" AND r.profesional.id = :profId");
                params.put("profId", profId);
            }

            if (reservaGet.getSpecialty() != null && !reservaGet.getSpecialty().isBlank()) {
                jpql.append(" AND LOWER(r.profesional.especialidad) = :specialty");
                params.put("specialty", reservaGet.getSpecialty().toLowerCase());
            }

            if (reservaGet.getDate() != null && !reservaGet.getDate().isBlank()) {
                jpql.append(" AND r.fecha = :fecha");
                params.put("fecha", stringToLocalDate(reservaGet.getDate()));
            }
        }

        TypedQuery<Reserva> query = entityManager.createQuery(jpql.toString(), Reserva.class);

        query.setParameter("estado", EstadoReservaEnum.CREADA);

        for (java.util.Map.Entry<String, Object> e : params.entrySet()) {
            query.setParameter(e.getKey(), e.getValue());
        }

        return query.getResultList();
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

    @Override
    public Uni<Boolean> validateOtrasReservasProfesional(Reserva reserva) {

        return Uni.createFrom().item(() -> validateOtrasReservasProfesionalBlocking(reserva))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected boolean validateOtrasReservasProfesionalBlocking(Reserva reserva) {

        String sql = "SELECT COUNT_BIG(r.Id) FROM Reserva r " +
                "WHERE r.ProfesionalId = :profId " +
                "AND r.ClienteId = :clieId " +
                "AND r.EstadoKd = :estado " +
                "AND r.Fecha = :fecha " +
                "AND r.HoraInicio <= :horaInicio " +
                "AND r.HoraFin >= :horaFin " +
                "AND ( :id IS NULL OR r.Id <> :id )";

        Long count = (Long) entityManager.createNativeQuery(sql)
                .setParameter("profId", reserva.getProfesional().getId())
                .setParameter("clieId", reserva.getCliente().getId())
                .setParameter("estado", EstadoReservaEnum.CREADA.getId())
                .setParameter("fecha", reserva.getFecha())
                .setParameter("horaInicio", localTimeToString(reserva.getHoraInicio()))
                .setParameter("horaFin", localTimeToString(reserva.getHoraFin()))
                .setParameter("id", reserva.getId())
                .getSingleResult();

        return count != null && count > 0;
    }
}

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
import org.acme.infraestructure.dtos.others.ReservaGet;

import java.util.List;
import java.util.UUID;

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

        StringBuilder jpql = new StringBuilder("SELECT r FROM Reserva r WHERE r.estado != :cancelada");
        java.util.Map<String, Object> params = new java.util.HashMap<>();

        if (reservaGet != null) {
            if (reservaGet.getIdClient() != null && !reservaGet.getIdClient().isBlank()) {
                try {
                    java.util.UUID clientId = java.util.UUID.fromString(reservaGet.getIdClient());
                    jpql.append(" AND r.cliente.id = :clientId");
                    params.put("clientId", clientId);
                } catch (IllegalArgumentException ignored) {
                    // valor inválido, ignorar el filtro
                }
            }

            // idProfessional -> filtrar por r.profesional.id
            if (reservaGet.getIdProfessional() != null && !reservaGet.getIdProfessional().isBlank()) {
                try {
                    java.util.UUID profId = java.util.UUID.fromString(reservaGet.getIdProfessional());
                    jpql.append(" AND r.profesional.id = :profId");
                    params.put("profId", profId);
                } catch (IllegalArgumentException ignored) {
                    // valor inválido, ignorar
                }
            }

            if (reservaGet.getSpecialty() != null && !reservaGet.getSpecialty().isBlank()) {
                jpql.append(" AND LOWER(r.profesional.especialidad) = :specialty");
                params.put("specialty", reservaGet.getSpecialty().toLowerCase());
            }

            if (reservaGet.getMinDate() != null && !reservaGet.getMinDate().isBlank()) {
                try {
                    java.time.LocalDate minDate = java.time.LocalDate.parse(reservaGet.getMinDate());
                    jpql.append(" AND r.fecha >= :minDate");
                    params.put("minDate", minDate);
                } catch (Exception ignored) {
                    // formato inválido, ignorar
                }
            }

            if (reservaGet.getMaxDate() != null && !reservaGet.getMaxDate().isBlank()) {
                try {
                    java.time.LocalDate maxDate = java.time.LocalDate.parse(reservaGet.getMaxDate());
                    jpql.append(" AND r.fecha <= :maxDate");
                    params.put("maxDate", maxDate);
                } catch (Exception ignored) {
                    // formato inválido, ignorar
                }
            }
        }

        jakarta.persistence.TypedQuery<Reserva> query = entityManager.createQuery(jpql.toString(), Reserva.class);
        query.setParameter("cancelada", EstadoReservaEnum.CANCELADA);
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
        String jpql = "SELECT COUNT(r) FROM Reserva r WHERE (r.profesional.id = :profId OR r.profesionalId = :profId) "
                + "AND r.estado = :estado AND (r.horaInicio < :horaFin AND r.horaFin > :horaInicio)";

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("profId", reserva.getProfesional().getId())
                .setParameter("estado", EstadoReservaEnum.CREADA)
                .setParameter("horaInicio", reserva.getHoraInicio())
                .setParameter("horaFin", reserva.getHoraFin())
                .getSingleResult();

        return count != null && count > 0;
    }
}

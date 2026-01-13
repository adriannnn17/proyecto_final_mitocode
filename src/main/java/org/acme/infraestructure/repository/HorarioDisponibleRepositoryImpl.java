package org.acme.infraestructure.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.acme.domain.model.entities.HorarioDisponible;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.domain.repository.HorarioDisponibleRepository;

import java.util.List;
import java.util.UUID;

import static org.acme.application.utils.MappingUtils.EstadoActivoEnumToBoolean;
import static org.acme.application.utils.MappingUtils.localTimeToString;

@ApplicationScoped
public class HorarioDisponibleRepositoryImpl implements HorarioDisponibleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Multi<HorarioDisponible> findAllInactivos() {
        return Uni.createFrom().item(this::findAllInactivosBlocking)
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transformToMulti(list -> Multi.createFrom().iterable(list));
    }

    @Transactional
    protected List<HorarioDisponible> findAllInactivosBlocking() {
        return find("estado", EstadoActivoEnum.ACTIVO).list();
    }

    @Override
    public Uni<HorarioDisponible> findByIdAndInactivo(UUID id) {
        return Uni.createFrom().item(() -> findByIdAndInactivoBlocking(id))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected HorarioDisponible findByIdAndInactivoBlocking(UUID id) {
        return find("id = ?1 and estado = ?2", id, EstadoActivoEnum.ACTIVO).firstResult();
    }

    @Override
    public Uni<Void> saveHorarioDisponible(HorarioDisponible horario) {
        return Uni.createFrom().item(() -> {
            saveHorarioDisponibleBlocking(horario);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void saveHorarioDisponibleBlocking(HorarioDisponible horario) {
        horario.setEstado(EstadoActivoEnum.ACTIVO);
        entityManager.persist(horario);
    }

    @Override
    public Uni<Void> updateHorarioDisponible(HorarioDisponible horario, UUID uuid) {
        return Uni.createFrom().item(() -> {
            updateHorarioDisponibleBlocking(horario, uuid);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void updateHorarioDisponibleBlocking(HorarioDisponible horario, UUID uuid) {
        HorarioDisponible managed = entityManager.find(HorarioDisponible.class, uuid);
        if (managed != null) {
            horario.setEstado(EstadoActivoEnum.ACTIVO);
            horario.setId(uuid);
            entityManager.merge(horario);
        }
    }

    @Override
    public Uni<Void> deleteHorarioDisponible(UUID uuid) {
        return Uni.createFrom().item(() -> {
            deleteHorarioDisponibleBlocking(uuid);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Override
    public Uni<Boolean> validateHorarioDisponibleHours(HorarioDisponible horarioDisponible) {

        return Uni.createFrom().item(() -> validateHorarioDisponibleHoursBlocking(horarioDisponible))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected boolean validateHorarioDisponibleHoursBlocking(HorarioDisponible horarioDisponible) {

        String sql = "SELECT COUNT_BIG(hd.Id) FROM HorarioDisponible hd " +
                "WHERE hd.ProfesionalId = :profId " +
                "AND hd.Estado = :estado " +
                "AND hd.Fecha = :fecha " +
                "AND hd.HoraInicio <= :horaInicio " +
                "AND hd.HoraFin >= :horaFin " +
                "AND ( :id IS NULL OR hd.Id <> :id )";

        Long count = (Long) entityManager.createNativeQuery(sql)
                .setParameter("profId", horarioDisponible.getProfesional().getId().toString())
                .setParameter("estado", EstadoActivoEnumToBoolean(EstadoActivoEnum.ACTIVO))
                .setParameter("fecha", horarioDisponible.getFecha())
                .setParameter("horaInicio", localTimeToString(horarioDisponible.getHoraInicio()))
                .setParameter("horaFin", localTimeToString(horarioDisponible.getHoraFin()))
                .setParameter("id", horarioDisponible.getId())
                .getSingleResult();

        return count != null && count > 0;
    }

    @Transactional
    protected void deleteHorarioDisponibleBlocking(UUID uuid) {
        HorarioDisponible managed = entityManager.find(HorarioDisponible.class, uuid);
        if (managed != null) {
            managed.setEstado(EstadoActivoEnum.INACTIVO);
            entityManager.merge(managed);
        }
    }

}

package org.acme.infraestructure.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.acme.domain.model.entities.Profesional;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.domain.repository.ProfesionalRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProfesionalRepositoryImpl implements ProfesionalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Multi<Profesional> findAllInactivos() {
        return Uni.createFrom().item(this::findAllInactivosBlocking)
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transformToMulti(list -> Multi.createFrom().iterable(list));
    }

    @Transactional
    protected List<Profesional> findAllInactivosBlocking() {
        return find("estadoActivo", EstadoActivoEnum.ACTIVO).list();
    }

    @Override
    public Uni<Profesional> findByIdAndInactivo(UUID id) {
        return Uni.createFrom().item(() -> findByIdAndInactivoBlocking(id))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected Profesional findByIdAndInactivoBlocking(UUID id) {
        return (Profesional) find("id = ?1 and estadoActivo = ?2", id, EstadoActivoEnum.ACTIVO).firstResult();
    }

    @Override
    public Uni<Void> saveProfesional(Profesional profesional) {
        return Uni.createFrom().item(() -> {
            saveProfesionalBlocking(profesional);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void saveProfesionalBlocking(Profesional profesional) {
        profesional.setEstadoActivo(EstadoActivoEnum.ACTIVO);
        entityManager.persist(profesional);
    }

    @Override
    public Uni<Void> updateProfesional(Profesional profesional, UUID uuid) {
        return Uni.createFrom().item(() -> {
            updateProfesionalBlocking(profesional, uuid);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void updateProfesionalBlocking(Profesional profesional, UUID uuid) {
        Profesional managed = entityManager.find(Profesional.class, uuid);
        if (managed != null) {
            profesional.setEstadoActivo(EstadoActivoEnum.ACTIVO);
            profesional.setId(uuid);
            entityManager.merge(profesional);
        }
    }

    @Override
    public Uni<Void> deleteProfesional(UUID uuid) {
        return Uni.createFrom().item(() -> {
            deleteProfesionalBlocking(uuid);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void deleteProfesionalBlocking(UUID uuid) {
        Profesional managed = entityManager.find(Profesional.class, uuid);
        if (managed != null) {
            managed.setEstadoActivo(EstadoActivoEnum.INACTIVO);
            entityManager.merge(managed);
        }
    }
}

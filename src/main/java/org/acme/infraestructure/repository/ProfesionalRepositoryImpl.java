package org.acme.infraestructure.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.acme.domain.model.entities.Profesional;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.domain.repository.ProfesionalRepository;

import java.util.UUID;

@ApplicationScoped
public class ProfesionalRepositoryImpl implements ProfesionalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Multi<Profesional> findAllInactivos() {
        return Multi.createFrom()
                .iterable(find("estadoActivo", EstadoActivoEnum.INACTIVO).list());
    }

    @Override
    public Uni<Profesional> findByIdAndInactivo(UUID id) {
        return find("id = ?1 and estadoActivo = ?2",
                id,
                EstadoActivoEnum.INACTIVO)
                .firstResult();
    }

    @Override
    public Uni<Void> saveProfesional(Profesional profesional) {
        return Uni.createFrom().item(() -> {
            entityManager.persist(profesional);
            return null;
        });
    }

    @Override
    public Uni<Void> updateProfesional(Profesional profesional, UUID uuid) {
        return Uni.createFrom().item(() -> {
            profesional.setId(uuid);
            entityManager.merge(profesional);
            return null;
        });
    }

    @Override
    public Uni<Void> deleteProfesional(UUID uuid) {
        return Uni.createFrom().item(() -> {
            Profesional managed = entityManager.find(Profesional.class, uuid);
            if (managed != null) {
                entityManager.remove(managed);
            }
            return null;
        });
    }
}

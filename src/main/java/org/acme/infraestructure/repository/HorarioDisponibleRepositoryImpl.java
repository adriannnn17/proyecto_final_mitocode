package org.acme.infraestructure.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.acme.domain.model.entities.HorarioDisponible;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.domain.repository.HorarioDisponibleRepository;

import java.util.UUID;

@ApplicationScoped
public class HorarioDisponibleRepositoryImpl implements HorarioDisponibleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Multi<HorarioDisponible> findAllInactivos() {
        return Multi.createFrom()
                .iterable(find("estadoActivo", EstadoActivoEnum.INACTIVO).list());
    }

    @Override
    public Uni<HorarioDisponible> findByIdAndInactivo(UUID id) {
        return find("id = ?1 and estadoActivo = ?2",
                id,
                EstadoActivoEnum.INACTIVO)
                .firstResult();
    }

    @Override
    public Uni<Void> saveHorarioDisponible(HorarioDisponible horario) {
        return Uni.createFrom().item(() -> {
            entityManager.persist(horario);
            return null;
        });
    }

    @Override
    public Uni<Void> updateHorarioDisponible(HorarioDisponible horario, UUID uuid) {
        return Uni.createFrom().item(() -> {
            horario.setId(uuid);
            entityManager.merge(horario);
            return null;
        });
    }

    @Override
    public Uni<Void> deleteHorarioDisponible(UUID uuid) {
        return Uni.createFrom().item(() -> {
            HorarioDisponible managed = entityManager.find(HorarioDisponible.class, uuid);
            if (managed != null) {
                entityManager.remove(managed);
            }
            return null;
        });
    }


}

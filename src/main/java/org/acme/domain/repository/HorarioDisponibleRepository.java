package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.entities.HorarioDisponible;

import java.util.UUID;

public interface HorarioDisponibleRepository extends PanacheRepositoryBase<HorarioDisponible, UUID> {

    Multi<HorarioDisponible> findAllInactivos();

    Uni<HorarioDisponible> findByIdAndInactivo(UUID id);

    Uni<Void> saveHorarioDisponible(HorarioDisponible horario);

    Uni<Void> updateHorarioDisponible(HorarioDisponible horario, UUID uuid);

    Uni<Void> deleteHorarioDisponible(UUID uuid);
}

package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.entities.Profesional;

import java.util.UUID;

public interface ProfesionalRepository extends PanacheRepositoryBase<Profesional, UUID> {

    Multi<Profesional> findAllInactivos();

    Uni<Profesional> findByIdAndInactivo(UUID id);

    Uni<Void> saveProfesional(Profesional profesional);

    Uni<Void> updateProfesional(Profesional profesional, UUID uuid);

    Uni<Void> deleteProfesional(UUID uuid);
}

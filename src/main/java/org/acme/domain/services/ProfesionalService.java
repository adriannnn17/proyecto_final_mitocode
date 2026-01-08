package org.acme.domain.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.infraestructure.dtos.ProfesionalDto;

import java.util.UUID;

public interface ProfesionalService {

    Multi<ProfesionalDto> listProfessionals();

    Uni<ProfesionalDto> findProfessional(UUID id);

    Uni<Void> createProfessional(ProfesionalDto profesional);

    Uni<Void> updateProfessional(ProfesionalDto profesional, UUID id);

    Uni<Void> deleteProfessional(UUID id);
}

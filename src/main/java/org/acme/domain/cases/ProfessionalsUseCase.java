package org.acme.domain.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.interfaces.resources.requests.ProfesionalSchemaRequest;
import org.acme.interfaces.resources.responses.ProfesionalSchemaResponse;

public interface ProfessionalsUseCase {

    Multi<ProfesionalSchemaResponse> listProfessionals();

    Uni<Void> createProfessional(ProfesionalSchemaRequest data);

    Uni<ProfesionalSchemaResponse> findProfessional(String idProfessional);

    Uni<Void> updateProfessional(String idProfessional, ProfesionalSchemaRequest data);

    Uni<Void> deleteProfessional(String idProfessional);
}


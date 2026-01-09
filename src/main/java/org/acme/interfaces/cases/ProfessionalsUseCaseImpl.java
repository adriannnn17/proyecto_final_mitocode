package org.acme.interfaces.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.mappers.ProfesionalMapper;
import org.acme.domain.cases.ProfessionalsUseCase;
import org.acme.domain.services.ProfesionalService;
import org.acme.interfaces.requests.ProfesionalSchemaRequest;
import org.acme.interfaces.responses.ProfesionalSchemaResponse;

import java.util.UUID;

@ApplicationScoped
public class ProfessionalsUseCaseImpl implements ProfessionalsUseCase {

    @Inject
    ProfesionalService profesionalService;
    ;

    @Override
    public Multi<ProfesionalSchemaResponse> listProfessionals() {
        return profesionalService.listProfessionals()
                .map(ProfesionalMapper.INSTANCE::toResponse);
    }

    @Override
    public Uni<Void> createProfessional(ProfesionalSchemaRequest data) {
        return profesionalService.createProfessional(ProfesionalMapper.INSTANCE.fromRequest(data));
    }

    @Override
    public Uni<ProfesionalSchemaResponse> findProfessional(String idProfessional) {
        return profesionalService.findProfessional(UUID.fromString(idProfessional))
                .map(ProfesionalMapper.INSTANCE::toResponse);
    }

    @Override
    public Uni<Void> updateProfessional(String idProfessional, ProfesionalSchemaRequest data) {
        return profesionalService.updateProfessional(ProfesionalMapper.INSTANCE.fromRequest(data),
                UUID.fromString(idProfessional));
    }

    @Override
    public Uni<Void> deleteProfessional(String idProfessional) {
        return profesionalService.deleteProfessional(UUID.fromString(idProfessional));
    }
}

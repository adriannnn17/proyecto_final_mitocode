package org.acme.interfaces.apis;

import jakarta.inject.Inject;
import org.acme.domain.cases.ProfessionalsUseCase;
import org.acme.reservas.api.ProfessionalsResource;
import org.acme.reservas.api.beans.ProfesionalSchemaRequest;
import org.acme.reservas.api.beans.ProfesionalSchemaResponse;

import java.util.List;
import java.util.concurrent.CompletionStage;

public class ProfessionalsResourceImpl implements ProfessionalsResource {

    @Inject
    ProfessionalsUseCase professionalsUseCase;

    @Override
    public CompletionStage<List<ProfesionalSchemaResponse>> listProfessional() {
        return professionalsUseCase.listProfessionals()
                .collect()
                .asList()
                .subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> createProfessional(ProfesionalSchemaRequest data) {
        return professionalsUseCase.createProfessional(data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<ProfesionalSchemaResponse> findProfessional(String idProfessional) {
        return professionalsUseCase.findProfessional(idProfessional).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> updateProfessional(String idProfessional, ProfesionalSchemaRequest data) {
        return professionalsUseCase.updateProfessional(idProfessional, data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> deleteProfessional(String idProfessional) {
        return professionalsUseCase.deleteProfessional(idProfessional).subscribeAsCompletionStage();
    }
}

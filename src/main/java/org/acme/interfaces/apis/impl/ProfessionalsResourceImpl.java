package org.acme.interfaces.apis.impl;

import jakarta.inject.Inject;
import org.acme.domain.cases.ProfessionalsUseCase;
import org.acme.interfaces.apis.ProfessionalsResource;
import org.acme.interfaces.resources.requests.ProfesionalSchemaRequest;
import org.acme.interfaces.resources.responses.ProfesionalSchemaResponse;

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
    public CompletionStage<Void> updateProfessional(String idProfessional,
                                                    ProfesionalSchemaRequest data) {
        return professionalsUseCase.updateProfessional(idProfessional, data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> deleteProfessional(String idProfessional) {
        return professionalsUseCase.deleteProfessional(idProfessional).subscribeAsCompletionStage();
    }
}

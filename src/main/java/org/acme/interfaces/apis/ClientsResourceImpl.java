package org.acme.interfaces.apis;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.acme.domain.cases.ClientsUseCase;
import org.acme.reservas.api.ClientsResource;
import org.acme.reservas.api.beans.ClienteSchemaRequest;
import org.acme.reservas.api.beans.ClienteSchemaResponse;

import java.util.List;
import java.util.concurrent.CompletionStage;

public class ClientsResourceImpl implements ClientsResource {

    @Inject
    ClientsUseCase clientsUseCase;

    @Override
    public CompletionStage<List<ClienteSchemaResponse>> listClients() {
        return clientsUseCase.listClients()
                .collect()
                .asList()
                .subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> createClients(@Valid ClienteSchemaRequest data) {
        return clientsUseCase.createClient(data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<ClienteSchemaResponse> findClients(@Valid String idClients) {
        return clientsUseCase.findClient(idClients).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> updateClients(@Valid String idClients,
                                               @Valid ClienteSchemaRequest data) {
        return clientsUseCase.updateClient(idClients, data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> deleteClients(@Valid String idClients) {
        return clientsUseCase.deleteClient(idClients).subscribeAsCompletionStage();
    }
}

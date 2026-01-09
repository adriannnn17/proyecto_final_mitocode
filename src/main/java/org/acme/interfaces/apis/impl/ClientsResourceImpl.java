package org.acme.interfaces.apis.impl;

import jakarta.inject.Inject;
import org.acme.domain.cases.ClientsUseCase;
import org.acme.interfaces.apis.ClientsResource;
import org.acme.interfaces.resources.requests.ClienteSchemaRequest;
import org.acme.interfaces.resources.responses.ClienteSchemaResponse;

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
    public CompletionStage<Void> createClients(ClienteSchemaRequest data) {
        return clientsUseCase.createClient(data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<ClienteSchemaResponse> findClients(String idClients) {
        return clientsUseCase.findClient(idClients).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> updateClients(String idClients,
                                               ClienteSchemaRequest data) {
        return clientsUseCase.updateClient(idClients, data).subscribeAsCompletionStage();
    }

    @Override
    public CompletionStage<Void> deleteClients(String idClients) {
        return clientsUseCase.deleteClient(idClients).subscribeAsCompletionStage();
    }
}

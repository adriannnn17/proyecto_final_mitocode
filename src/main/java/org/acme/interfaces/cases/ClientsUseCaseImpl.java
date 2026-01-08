package org.acme.interfaces.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.mappers.ClienteMapper;
import org.acme.domain.cases.ClientsUseCase;
import org.acme.domain.services.ClienteService;
import org.acme.reservas.api.beans.ClienteSchemaRequest;
import org.acme.reservas.api.beans.ClienteSchemaResponse;

import java.util.UUID;

@ApplicationScoped
public class ClientsUseCaseImpl implements ClientsUseCase {

    @Inject
    ClienteService clienteService;

    @Inject
    ClienteMapper appMapper;

    @Override
    public Multi<ClienteSchemaResponse> listClients() {
        return clienteService.listClients()
                .map(appMapper::toResponse);
    }

    @Override
    public Uni<Void> createClient(ClienteSchemaRequest data) {
        return clienteService.createClient(appMapper.fromRequest(data));
    }

    @Override
    public Uni<ClienteSchemaResponse> findClient(String idClients) {
        return clienteService.findClient(UUID.fromString(idClients))
                .map(appMapper::toResponse);
    }

    @Override
    public Uni<Void> updateClient(String idClients, ClienteSchemaRequest data) {
        return clienteService.updateClient(appMapper.fromRequest(data), UUID.fromString(idClients));
    }

    @Override
    public Uni<Void> deleteClient(String idClients) {
        return clienteService.deleteClient(UUID.fromString(idClients));
    }
}

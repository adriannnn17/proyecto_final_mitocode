package org.acme.domain.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.interfaces.resources.requests.ClienteSchemaRequest;
import org.acme.interfaces.resources.responses.ClienteSchemaResponse;

public interface ClientsUseCase {

    Multi<ClienteSchemaResponse> listClients();

    Uni<Void> createClient(ClienteSchemaRequest data);

    Uni<ClienteSchemaResponse> findClient(String idClients);

    Uni<Void> updateClient(String idClients, ClienteSchemaRequest data);

    Uni<Void> deleteClient(String idClients);
}


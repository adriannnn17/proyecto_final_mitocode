package org.acme.domain.cases;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.reservas.api.beans.ClienteSchemaRequest;
import org.acme.reservas.api.beans.ClienteSchemaResponse;

public interface ClientsUseCase {

    Multi<ClienteSchemaResponse> listClients();

    Uni<Void> createClient(ClienteSchemaRequest data);

    Uni<ClienteSchemaResponse> findClient(String idClients);

    Uni<Void> updateClient(String idClients, ClienteSchemaRequest data);

    Uni<Void> deleteClient(String idClients);
}


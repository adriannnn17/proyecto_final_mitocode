package org.acme.domain.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.infraestructure.dtos.ClienteDto;

import java.util.UUID;

public interface ClienteService {

    Multi<ClienteDto> listClients();

    Uni<ClienteDto> findClient(UUID id);

    Uni<Void> createClient(ClienteDto cliente);

    Uni<Void> updateClient(ClienteDto cliente, UUID id);

    Uni<Void> deleteClient(UUID id);
}

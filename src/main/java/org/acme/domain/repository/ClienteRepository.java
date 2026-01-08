package org.acme.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.entities.Cliente;

import java.util.UUID;

public interface ClienteRepository extends PanacheRepositoryBase<Cliente, UUID> {

    Multi<Cliente> findAllInactivos();

    Uni<Cliente> findByIdAndInactivo(UUID id);

    Uni<Void> saveClient(Cliente cliente);

    Uni<Void> updateClient(Cliente cliente, UUID uuid);

    Uni<Void> deleteClient(UUID uuid);
}

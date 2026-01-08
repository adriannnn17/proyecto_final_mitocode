package org.acme.infraestructure.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.acme.domain.model.entities.Cliente;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.domain.repository.ClienteRepository;

import java.util.UUID;

@ApplicationScoped
public class ClienteRepositoryImpl implements ClienteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Multi<Cliente> findAllInactivos() {
        return Multi.createFrom()
                .iterable(find("estadoActivo", EstadoActivoEnum.INACTIVO).list());
    }

    @Override
    public Uni<Cliente> findByIdAndInactivo(UUID id) {
        return find("id = ?1 and estadoActivo = ?2",
                id,
                EstadoActivoEnum.INACTIVO)
                .firstResult();
    }

    @Override
    public Uni<Void> saveClient(Cliente cliente) {
        return Uni.createFrom().item(() -> {
            entityManager.persist(cliente);
            return null;
        });
    }

    @Override
    public Uni<Void> updateClient(Cliente cliente, UUID uuid) {
        return Uni.createFrom().item(() -> {
            cliente.setId(uuid);
            entityManager.merge(cliente);
            return null;
        });
    }

    @Override
    public Uni<Void> deleteClient(UUID uuid) {
        return Uni.createFrom().item(() -> {
            Cliente managed = entityManager.find(Cliente.class, uuid);
            if (managed != null) {
                entityManager.remove(managed);
            }
            return null;
        });
    }
}

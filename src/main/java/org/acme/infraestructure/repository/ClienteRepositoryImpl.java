package org.acme.infraestructure.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.acme.domain.model.entities.Cliente;
import org.acme.domain.model.enums.EstadoActivoEnum;
import org.acme.domain.repository.ClienteRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ClienteRepositoryImpl implements ClienteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Multi<Cliente> findAllInactivos() {
        return Uni.createFrom().item(this::findAllInactivosBlocking).runSubscriptionOn(Infrastructure.getDefaultWorkerPool()).onItem().transformToMulti(list -> Multi.createFrom().iterable(list));
    }

    @Transactional
    protected List<Cliente> findAllInactivosBlocking() {
        return find("estadoActivo", EstadoActivoEnum.ACTIVO).list();
    }

    @Override
    public Uni<Cliente> findByIdAndInactivo(UUID id) {
        return Uni.createFrom().item(() -> findByIdAndInactivoBlocking(id)).runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected Cliente findByIdAndInactivoBlocking(UUID id) {
        return find("id = ?1 and estadoActivo = ?2", id, EstadoActivoEnum.ACTIVO).firstResult();
    }

    @Override
    public Uni<Void> saveClient(Cliente cliente) {
        return Uni.createFrom().item(() -> {
            this.saveClientBlocking(cliente);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void saveClientBlocking(Cliente cliente) {
        cliente.setEstadoActivo(EstadoActivoEnum.ACTIVO);
        entityManager.persist(cliente);
    }

    @Override
    public Uni<Void> updateClient(Cliente cliente, UUID uuid) {
        return Uni.createFrom().item(() -> {
            updateClientBlocking(cliente, uuid);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void updateClientBlocking(Cliente cliente, UUID uuid) {
        Cliente managed = entityManager.find(Cliente.class, uuid);
        if (managed != null) {
            cliente.setId(uuid);
            cliente.setEstadoActivo(EstadoActivoEnum.ACTIVO);
            entityManager.merge(cliente);
        }
    }

    @Override
    public Uni<Void> deleteClient(UUID uuid) {
        return Uni.createFrom().item(() -> {
            deleteClientBlocking(uuid);
            return null;
        }).replaceWithVoid().runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

    @Transactional
    protected void deleteClientBlocking(UUID uuid) {
        Cliente managed = entityManager.find(Cliente.class, uuid);
        if (managed != null) {
            managed.setEstadoActivo(EstadoActivoEnum.INACTIVO);
            entityManager.merge(managed);
        }
    }

}

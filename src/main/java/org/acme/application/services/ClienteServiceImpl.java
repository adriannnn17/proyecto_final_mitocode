package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.repository.ClienteRepository;
import org.acme.domain.services.ClienteService;
import org.acme.infraestructure.dtos.ClienteDto;
import org.acme.infraestructure.mappers.ClienteMapper;

import java.util.UUID;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Override
    public Multi<ClienteDto> listClients() {
        return clienteRepository.findAllInactivos().map(ClienteMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<ClienteDto> findClient(UUID id) {
        return clienteRepository.findByIdAndInactivo(id).map(ClienteMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<Void> createClient(ClienteDto cliente) {
        return clienteRepository.saveClient(ClienteMapper.INSTANCE.toEntity(cliente));
    }

    @Override
    public Uni<Void> updateClient(ClienteDto cliente, UUID id) {
        return clienteRepository.updateClient(ClienteMapper.INSTANCE.toEntity(cliente), id);
    }

    @Override
    public Uni<Void> deleteClient(UUID id) {
        return clienteRepository.deleteClient(id);
    }
}

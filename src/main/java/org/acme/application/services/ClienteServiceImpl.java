package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.repository.ClienteRepository;
import org.acme.domain.services.ClienteService;
import org.acme.infraestructure.dtos.ClienteDto;
import org.acme.infraestructure.mappers.ClienteEntityDtoMapper;

import java.util.UUID;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Override
    public Multi<ClienteDto> listClients() {
        return clienteRepository.findAllInactivos().map(ClienteEntityDtoMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<ClienteDto> findClient(UUID id) {
        return clienteRepository.findByIdAndInactivo(id).map(ClienteEntityDtoMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<Void> createClient(ClienteDto cliente) {
        return clienteRepository.saveClient(ClienteEntityDtoMapper.INSTANCE.toEntity(cliente));
    }

    @Override
    public Uni<Void> updateClient(ClienteDto cliente, UUID id) {
        return clienteRepository.updateClient(ClienteEntityDtoMapper.INSTANCE.toEntity(cliente), id);
    }

    @Override
    public Uni<Void> deleteClient(UUID id) {
        return clienteRepository.deleteClient(id);
    }
}

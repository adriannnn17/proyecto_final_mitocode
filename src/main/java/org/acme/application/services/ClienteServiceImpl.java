package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.domain.repository.ClienteRepository;
import org.acme.domain.services.ClienteService;
import org.acme.infraestructure.dtos.ClienteDto;
import org.acme.infraestructure.exceptions.BusinessErrorType;
import org.acme.infraestructure.exceptions.BusinessException;
import org.acme.infraestructure.mappers.ClienteEntityDtoMapper;

import java.util.UUID;

@Slf4j
@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Override
    public Multi<ClienteDto> listClients() {
        return clienteRepository.findAllInactivos()
                .map(ClienteEntityDtoMapper.INSTANCE::toDto)
                .onFailure()
                .transform(throwable -> {

                    log.error(throwable.getMessage());

                    return new BusinessException(
                            BusinessErrorType.PERSISTENCE_ERROR,
                            throwable.getMessage()
                    );
                });
    }

    @Override
    public Uni<ClienteDto> findClient(UUID id) {
        return clienteRepository.findByIdAndInactivo(id)
                .map(ClienteEntityDtoMapper.INSTANCE::toDto)
                .onFailure()
                .transform(throwable -> {

                    log.error(throwable.getMessage());

                    return new BusinessException(
                            BusinessErrorType.PERSISTENCE_ERROR,
                            throwable.getMessage()
                    );
                });
    }

    @Override
    public Uni<Void> createClient(ClienteDto cliente) {
        return clienteRepository.saveClient(ClienteEntityDtoMapper.INSTANCE.toEntity(cliente))
                .onFailure()
                .transform(throwable -> {

                    log.error(throwable.getMessage());

                    return new BusinessException(
                            BusinessErrorType.PERSISTENCE_ERROR,
                            throwable.getMessage()
                    );
                });
    }

    @Override
    public Uni<Void> updateClient(ClienteDto cliente, UUID id) {
        return clienteRepository.updateClient(ClienteEntityDtoMapper.INSTANCE.toEntity(cliente), id)
                .onFailure()
                .transform(throwable -> {

                    log.error(throwable.getMessage());

                    return new BusinessException(
                            BusinessErrorType.PERSISTENCE_ERROR,
                            throwable.getMessage()
                    );
                });
    }

    @Override
    public Uni<Void> deleteClient(UUID id) {
        return clienteRepository.deleteClient(id)
                .onFailure()
                .transform(throwable -> {

                    log.error(throwable.getMessage());

                    return new BusinessException(
                            BusinessErrorType.PERSISTENCE_ERROR,
                            throwable.getMessage()
                    );
                });
    }
}

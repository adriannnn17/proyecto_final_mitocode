package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.repository.ProfesionalRepository;
import org.acme.domain.services.ProfesionalService;
import org.acme.infraestructure.dtos.ProfesionalDto;
import org.acme.infraestructure.exceptions.BusinessErrorType;
import org.acme.infraestructure.exceptions.BusinessException;
import org.acme.infraestructure.mappers.ProfesionalEntityDtoMapper;

import java.util.UUID;

@ApplicationScoped
public class ProfesionalServiceImpl implements ProfesionalService {

    @Inject
    ProfesionalRepository profesionalRepository;


    @Override
    public Multi<ProfesionalDto> listProfessionals() {
        return profesionalRepository.findAllInactivos().map(ProfesionalEntityDtoMapper.INSTANCE::toDto)
                .onFailure()
                .transform(throwable ->
                        new BusinessException(
                                BusinessErrorType.PERSISTENCE_ERROR,
                                throwable.getMessage()
                        )
                );
    }

    @Override
    public Uni<ProfesionalDto> findProfessional(UUID id) {
        return profesionalRepository.findByIdAndInactivo(id).map(ProfesionalEntityDtoMapper.INSTANCE::toDto)
                .onFailure()
                .transform(throwable ->
                        new BusinessException(
                                BusinessErrorType.PERSISTENCE_ERROR,
                                throwable.getMessage()
                        )
                );
    }

    @Override
    public Uni<Void> createProfessional(ProfesionalDto profesional) {
        return profesionalRepository.saveProfesional(ProfesionalEntityDtoMapper.INSTANCE.toEntity(profesional))
                .onFailure()
                .transform(throwable ->
                        new BusinessException(
                                BusinessErrorType.PERSISTENCE_ERROR,
                                throwable.getMessage()
                        )
                );
    }

    @Override
    public Uni<Void> updateProfessional(ProfesionalDto profesional, UUID id) {
        return profesionalRepository.updateProfesional(ProfesionalEntityDtoMapper.INSTANCE.toEntity(profesional), id)
                .onFailure()
                .transform(throwable ->
                        new BusinessException(
                                BusinessErrorType.PERSISTENCE_ERROR,
                                throwable.getMessage()
                        )
                );
    }

    @Override
    public Uni<Void> deleteProfessional(UUID id) {
        return profesionalRepository.deleteProfesional(id)
                .onFailure()
                .transform(throwable ->
                        new BusinessException(
                                BusinessErrorType.PERSISTENCE_ERROR,
                                throwable.getMessage()
                        )
                );
    }
}

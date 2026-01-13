package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.domain.model.entities.Profesional;
import org.acme.domain.repository.HorarioDisponibleRepository;
import org.acme.domain.repository.ProfesionalRepository;
import org.acme.domain.services.HorarioDisponibleService;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;
import org.acme.infraestructure.exceptions.BusinessErrorType;
import org.acme.infraestructure.exceptions.BusinessException;
import org.acme.infraestructure.mappers.HorarioDisponibleEntityDtoMapper;

import java.util.UUID;

@Slf4j
@ApplicationScoped
public class HorarioDisponibleServiceImpl implements HorarioDisponibleService {

    @Inject
    HorarioDisponibleRepository horarioDisponibleRepository;

    @Inject
    ProfesionalRepository profesionalRepository;

    @Override
    public Multi<HorarioDisponibleDto> listAvailabilityHours() {
        return horarioDisponibleRepository.findAllInactivos().map(HorarioDisponibleEntityDtoMapper.INSTANCE::toDto)
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
    public Uni<HorarioDisponibleDto> findAvailabilityHour(UUID id) {
        return horarioDisponibleRepository.findByIdAndInactivo(id)
                .onItem()
                .ifNull()
                .failWith(() -> new BusinessException(
                        BusinessErrorType.NOT_FOUND,
                        "Horario con id %s no encontrado".formatted(id)
                ))
                .onItem()
                .ifNotNull()
                .transform(HorarioDisponibleEntityDtoMapper.INSTANCE::toDto)
                .onFailure(t -> !(t instanceof BusinessException))
                .transform(throwable -> {

                    if (throwable instanceof BusinessException) {
                        return throwable;
                    }
                    log.error(throwable.getMessage());
                    return new BusinessException(
                            BusinessErrorType.PERSISTENCE_ERROR,
                            throwable.getMessage()
                    );
                });
    }

    @Override
    public Uni<Void> createAvailabilityHour(HorarioDisponibleDto horario) {

        return validateHorarioDisponible(horario,
                horarioDisponibleRepository.saveHorarioDisponible(HorarioDisponibleEntityDtoMapper.INSTANCE.toEntity(horario))
                        .onFailure()
                        .transform(throwable -> {

                            log.error(throwable.getMessage());

                            return new BusinessException(
                                    BusinessErrorType.PERSISTENCE_ERROR,
                                    throwable.getMessage()
                            );
                        }));
    }

    @Override
    public Uni<Void> updateAvailabilityHour(HorarioDisponibleDto horario, UUID id) {

        return findAvailabilityHour(id)
                .flatMap(horarioDisponibleDto ->
                        validateHorarioDisponible(horario,
                                horarioDisponibleRepository.updateHorarioDisponible(HorarioDisponibleEntityDtoMapper.INSTANCE.toEntity(horario), id)
                                        .onFailure()
                                        .transform(throwable -> {

                                            log.error(throwable.getMessage());

                                            return new BusinessException(
                                                    BusinessErrorType.PERSISTENCE_ERROR,
                                                    throwable.getMessage()
                                            );
                                        })));
    }

    @Override
    public Uni<Void> deleteAvailabilityHour(UUID id) {
        return findAvailabilityHour(id)
                .flatMap(horarioDisponibleDto ->
                        horarioDisponibleRepository.deleteHorarioDisponible(id)
                                .onFailure()
                                .transform(throwable -> {

                                    log.error(throwable.getMessage());

                                    return new BusinessException(
                                            BusinessErrorType.PERSISTENCE_ERROR,
                                            throwable.getMessage()
                                    );
                                }));
    }

    public Uni<Void> validateHorarioDisponible(HorarioDisponibleDto horario, Uni<Void> voidUni) {

        return Uni.combine()
                .all()
                .unis(
                        Uni.createFrom().item(horario.getHoraInicio().isBefore(horario.getHoraFin())),
                        profesionalRepository.findByIdAndInactivo(horario.getProfesionalId())
                                .onFailure()
                                .transform(throwable -> {

                                    log.error(throwable.getMessage());

                                    return new BusinessException(
                                            BusinessErrorType.PERSISTENCE_ERROR,
                                            throwable.getMessage()
                                    );
                                }),
                        horarioDisponibleRepository.validateHorarioDisponibleHours(HorarioDisponibleEntityDtoMapper.INSTANCE.toEntity(horario))
                                .onFailure()
                                .transform(throwable -> {

                                    log.error(throwable.getMessage());

                                    return new BusinessException(
                                            BusinessErrorType.PERSISTENCE_ERROR,
                                            throwable.getMessage()
                                    );
                                })
                ).asTuple().flatMap(objects -> {
                    Boolean fechaValidacion = objects.getItem1();
                    Profesional profesional = objects.getItem2();
                    Boolean existsHorario = objects.getItem3();

                    if (!fechaValidacion) {
                        return Uni.createFrom().failure(
                                new BusinessException(
                                        BusinessErrorType.VALIDATION_ERROR,
                                        "La hora de inicio debe ser anterior a la hora de fin"
                                )
                        );
                    }

                    if (profesional == null) {
                        return Uni.createFrom().failure(
                                new BusinessException(BusinessErrorType.VALIDATION_ERROR, "El profesional no esta activo en el sistema")
                        );
                    }

                    if (existsHorario) {
                        return Uni.createFrom().failure(
                                new BusinessException(BusinessErrorType.VALIDATION_ERROR, "El horario disponible ya existe en el sistema")
                        );
                    }

                    return voidUni;
                });
    }
}

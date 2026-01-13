package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.domain.model.entities.Cliente;
import org.acme.domain.model.entities.Profesional;
import org.acme.domain.repository.ClienteRepository;
import org.acme.domain.repository.HorarioDisponibleRepository;
import org.acme.domain.repository.ProfesionalRepository;
import org.acme.domain.repository.ReservaRepository;
import org.acme.domain.services.ReservaService;
import org.acme.infraestructure.dtos.ReservaDto;
import org.acme.infraestructure.dtos.others.ReservaGet;
import org.acme.infraestructure.exceptions.BusinessErrorType;
import org.acme.infraestructure.exceptions.BusinessException;
import org.acme.infraestructure.mappers.HorarioDisponibleEntityDtoMapper;
import org.acme.infraestructure.mappers.ReservaEntityDtoMapper;

import java.util.UUID;

import static org.acme.application.utils.MappingUtils.mapReservaToHorarioDisponibleHours;

@Slf4j
@ApplicationScoped
public class ReservaServiceImpl implements ReservaService {

    @Inject
    ReservaRepository reservaRepository;

    @Inject
    ProfesionalRepository profesionalRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    HorarioDisponibleRepository horarioDisponibleRepository;

    @Override
    public Multi<ReservaDto> listBookings(ReservaGet reservaGet) {
        return reservaRepository.findAllByEstado(reservaGet).map(ReservaEntityDtoMapper.INSTANCE::toDto)
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
    public Uni<ReservaDto> findBooking(UUID id) {
        return reservaRepository.findByEstado(id)
                .onItem()
                .ifNull()
                .failWith(() -> new BusinessException(
                        BusinessErrorType.NOT_FOUND,
                        "Cliente con id %s no encontrado".formatted(id)
                ))
                .onItem()
                .ifNotNull()
                .transform(ReservaEntityDtoMapper.INSTANCE::toDto)
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
    public Uni<Void> createBooking(ReservaDto reserva) {
        return validateConditional(reserva)
                .onItem().ignore()
                .andSwitchTo(reservaRepository.saveReserva(ReservaEntityDtoMapper.INSTANCE.toEntity(reserva)).onFailure()
                        .transform(throwable -> {

                            log.error(throwable.getMessage());

                            return new BusinessException(
                                    BusinessErrorType.PERSISTENCE_ERROR,
                                    throwable.getMessage()
                            );
                        }));
    }

    @Override
    public Uni<Void> updateBooking(ReservaDto reserva, UUID id) {
        reserva.setId(id);
        return findBooking(id)
                .flatMap(reservaDto ->
                        validateConditional(reserva)
                                .onItem().ignore()
                                .andSwitchTo(reservaRepository.updateReserva(ReservaEntityDtoMapper.INSTANCE.toEntity(reserva), id)
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
    public Uni<Void> deleteBooking(UUID id) {
        return findBooking(id)
                .flatMap(reservaDto ->
                        reservaRepository.deleteReserva(id)
                                .onFailure()
                                .transform(throwable -> {

                                    log.error(throwable.getMessage());

                                    return new BusinessException(
                                            BusinessErrorType.PERSISTENCE_ERROR,
                                            throwable.getMessage()
                                    );
                                }));
    }

    public Uni<Void> validateConditional(ReservaDto reservaDto) {

        return validateProfesionalCliente(reservaDto)
                .onItem().ignore().andSwitchTo(
                        Uni.combine()
                                .all()
                                .unis(
                                        Uni.createFrom().item(reservaDto.getHoraInicio().isBefore(reservaDto.getHoraFin())),
                                        horarioDisponibleRepository.validateHorarioDisponibleHours(
                                                        HorarioDisponibleEntityDtoMapper.INSTANCE
                                                                .toEntity(mapReservaToHorarioDisponibleHours(reservaDto))
                                                )
                                                .onFailure()
                                                .transform(throwable -> {

                                                    log.error(throwable.getMessage());

                                                    return new BusinessException(
                                                            BusinessErrorType.PERSISTENCE_ERROR,
                                                            throwable.getMessage()
                                                    );
                                                }),
                                        reservaRepository.validateOtrasReservasProfesional(
                                                        ReservaEntityDtoMapper.INSTANCE.toEntity(reservaDto)
                                                )
                                                .onFailure()
                                                .transform(throwable -> {

                                                    log.error(throwable.getMessage());

                                                    return new BusinessException(
                                                            BusinessErrorType.PERSISTENCE_ERROR,
                                                            throwable.getMessage()
                                                    );
                                                })
                                )
                                .asTuple()
                )
                .onItem().transformToUni(tuple -> {
                    Boolean fechaValidacion = tuple.getItem1();
                    Boolean horarioDisponible = tuple.getItem2();
                    Boolean otrasReservas = tuple.getItem3();

                    if (!fechaValidacion) {
                        return Uni.createFrom().failure(
                                new BusinessException(
                                        BusinessErrorType.VALIDATION_ERROR,
                                        "La hora de inicio debe ser anterior a la hora de fin"
                                )
                        );
                    }

                    if (!horarioDisponible) {
                        return Uni.createFrom().failure(
                                new BusinessException(
                                        BusinessErrorType.VALIDATION_ERROR,
                                        "El horario no esta disponible"
                                )
                        );
                    }

                    if (otrasReservas) {
                        return Uni.createFrom().failure(
                                new BusinessException(
                                        BusinessErrorType.VALIDATION_ERROR,
                                        "El profesional ya tiene otra reserva en ese horario"
                                )
                        );
                    }

                    return Uni.createFrom().voidItem();
                });
    }


    public Uni<Void> validateProfesionalCliente(ReservaDto reservaDto) {

        return Uni.combine()
                .all()
                .unis(
                        profesionalRepository.findByIdAndInactivo(reservaDto.getProfesionalId())
                                .onFailure()
                                .transform(throwable ->
                                        new BusinessException(
                                                BusinessErrorType.PERSISTENCE_ERROR,
                                                throwable.getMessage()
                                        )
                                ),
                        clienteRepository.findByIdAndInactivo(reservaDto.getClienteId())
                                .onFailure()
                                .transform(throwable ->
                                        new BusinessException(
                                                BusinessErrorType.PERSISTENCE_ERROR,
                                                throwable.getMessage()
                                        )
                                )
                )
                .asTuple()
                .onItem().transformToUni(tuple -> {
                    Profesional prof = tuple.getItem1();
                    Cliente cli = tuple.getItem2();

                    if (prof == null) {
                        return Uni.createFrom().failure(
                                new BusinessException(
                                        BusinessErrorType.VALIDATION_ERROR,
                                        "El profesional no esta activo en el sistema"
                                )
                        );
                    }

                    if (cli == null) {
                        return Uni.createFrom().failure(
                                new BusinessException(
                                        BusinessErrorType.VALIDATION_ERROR,
                                        "El cliente no esta activo en el sistema"
                                )
                        );
                    }

                    return Uni.createFrom().voidItem();
                });
    }

}

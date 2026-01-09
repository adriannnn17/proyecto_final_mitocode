package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.domain.model.entities.Cliente;
import org.acme.domain.model.entities.Profesional;
import org.acme.domain.repository.ClienteRepository;
import org.acme.domain.repository.HorarioDisponibleRepository;
import org.acme.domain.repository.ProfesionalRepository;
import org.acme.domain.repository.ReservaRepository;
import org.acme.domain.services.ReservaService;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;
import org.acme.infraestructure.dtos.ReservaDto;
import org.acme.infraestructure.dtos.others.ReservaGet;
import org.acme.infraestructure.exceptions.BusinessErrorType;
import org.acme.infraestructure.exceptions.BusinessException;
import org.acme.infraestructure.mappers.HorarioDisponibleEntityDtoMapper;
import org.acme.infraestructure.mappers.ReservaEntityDtoMapper;

import java.util.UUID;

import static org.acme.application.utils.MappingUtils.mapReservaToHorarioDisponibleHours;

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
        return reservaRepository.findAllByEstado(reservaGet).map(ReservaEntityDtoMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<ReservaDto> findBooking(UUID id) {
        return reservaRepository.findByEstado(id).map(ReservaEntityDtoMapper.INSTANCE::toDto);
    }

    @Override
    public Uni<Void> createBooking(ReservaDto reserva) {
        return validateConditional(reserva)
                .onItem().ignore()
                .andSwitchTo(reservaRepository.saveReserva(ReservaEntityDtoMapper.INSTANCE.toEntity(reserva)));
    }

    @Override
    public Uni<Void> updateBooking(ReservaDto reserva, UUID id) {
        return validateConditional(reserva)
                .onItem().ignore()
                .andSwitchTo(reservaRepository.updateReserva(ReservaEntityDtoMapper.INSTANCE.toEntity(reserva), id));
    }

    @Override
    public Uni<Void> deleteBooking(UUID id) {
        return reservaRepository.deleteReserva(id);
    }

    public Uni<Void> validateConditional(ReservaDto reservaDto) {

        return validateProfesionalCliente(reservaDto)
                .onItem().ignore().andSwitchTo(
                        Uni.combine()
                                .all()
                                .unis(
                                        horarioDisponibleRepository.validateHorarioDisponibleHours(
                                                HorarioDisponibleEntityDtoMapper.INSTANCE
                                                        .toEntity(mapReservaToHorarioDisponibleHours(reservaDto))
                                        ),
                                        reservaRepository.validateOtrasReservasProfesional(
                                                ReservaEntityDtoMapper.INSTANCE.toEntity(reservaDto)
                                        )
                                )
                                .asTuple()
                )
                .onItem().transformToUni(tuple -> {
                    Boolean horarioDisponible = tuple.getItem1();
                    Boolean otrasReservas = tuple.getItem2();

                    if (!horarioDisponible) {
                        return Uni.createFrom().failure(
                                new BusinessException(
                                        BusinessErrorType.VALIDATION_ERROR,
                                        "El horario no esta disponible"
                                )
                        );
                    }

                    if (!otrasReservas) {
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
                        profesionalRepository.findByIdAndInactivo(reservaDto.getProfesionalId()),
                        clienteRepository.findByIdAndInactivo(reservaDto.getClienteId())
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

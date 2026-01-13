package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.entities.Cliente;
import org.acme.domain.model.entities.Profesional;
import org.acme.domain.model.entities.Reserva;
import org.acme.domain.repository.ClienteRepository;
import org.acme.domain.repository.HorarioDisponibleRepository;
import org.acme.domain.repository.ProfesionalRepository;
import org.acme.domain.repository.ReservaRepository;
import org.acme.infraestructure.dtos.ReservaDto;
import org.acme.infraestructure.dtos.others.ReservaGet;
import org.acme.infraestructure.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceImplTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ProfesionalRepository profesionalRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private HorarioDisponibleRepository horarioRepository;

    @InjectMocks
    private ReservaServiceImpl service;

    @BeforeEach
    public void setup() {
        // Mockito annotations inicializan los mocks e inyectan en 'service'
    }

    @Test
    public void testListBookings_repositoryFailure_shouldTransformToBusinessException() {
        ReservaGet rg = new ReservaGet();

        when(reservaRepository.findAllByEstado(Mockito.any())).thenReturn(Multi.createFrom().failure(new RuntimeException("db error")));

        assertThrows(BusinessException.class, () -> service.listBookings(rg).collect().first().await().indefinitely());
    }

    @Test
    public void testListBookings_success_shouldReturnWithoutException() {
        ReservaGet rg = new ReservaGet();

        when(reservaRepository.findAllByEstado(Mockito.any())).thenReturn(Multi.createFrom().item(Mockito.mock(Reserva.class)));

        assertDoesNotThrow(() -> service.listBookings(rg).collect().first().await().indefinitely());
    }

    @Test
    public void testFindBooking_repositoryFailure_shouldTransformToBusinessException() {
        UUID id = UUID.randomUUID();
        when(reservaRepository.findByEstado(Mockito.any())).thenReturn(Uni.createFrom().failure(new RuntimeException("db error")));

        assertThrows(BusinessException.class, () -> service.findBooking(id).await().indefinitely());
    }

    @Test
    public void testFindBooking_success_shouldReturnWithoutException() {
        UUID id = UUID.randomUUID();
        when(reservaRepository.findByEstado(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Reserva.class)));

        assertDoesNotThrow(() -> service.findBooking(id).await().indefinitely());
    }

    @Test
    public void testCreateBooking_success() {
        ReservaDto dto = new ReservaDto();
        dto.setId(UUID.randomUUID());
        dto.setFecha(LocalDate.of(2026,1,10));
        dto.setHoraInicio(LocalTime.of(10,0));
        dto.setHoraFin(LocalTime.of(11,0));
        dto.setProfesionalId(UUID.randomUUID());
        dto.setClienteId(UUID.randomUUID());

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(clienteRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Cliente.class)));
        when(horarioRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(true));
        when(reservaRepository.validateOtrasReservasProfesional(Mockito.any())).thenReturn(Uni.createFrom().item(false));
        when(reservaRepository.saveReserva(Mockito.any())).thenReturn(Uni.createFrom().voidItem());

        // No debe lanzar excepción
        assertDoesNotThrow(() -> service.createBooking(dto).await().indefinitely());
    }

    @Test
    public void testCreateBooking_profesionalInactivo_shouldThrowValidation() {
        ReservaDto dto = new ReservaDto();
        dto.setId(UUID.randomUUID());
        dto.setFecha(LocalDate.of(2026,1,10));
        dto.setHoraInicio(LocalTime.of(10,0));
        dto.setHoraFin(LocalTime.of(11,0));
        dto.setProfesionalId(UUID.randomUUID());
        dto.setClienteId(UUID.randomUUID());

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item((Profesional) null));
        when(clienteRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Cliente.class)));

        assertThrows(BusinessException.class, () -> service.createBooking(dto).await().indefinitely());
    }

    @Test
    public void testCreateBooking_clienteInactivo_shouldThrowValidation() {
        ReservaDto dto = new ReservaDto();
        dto.setId(UUID.randomUUID());
        dto.setFecha(LocalDate.of(2026,1,10));
        dto.setHoraInicio(LocalTime.of(10,0));
        dto.setHoraFin(LocalTime.of(11,0));
        dto.setProfesionalId(UUID.randomUUID());
        dto.setClienteId(UUID.randomUUID());

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(clienteRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item((Cliente) null));

        assertThrows(BusinessException.class, () -> service.createBooking(dto).await().indefinitely());
    }

    @Test
    public void testCreateBooking_horarioNoDisponible_shouldThrowValidation() {
        ReservaDto dto = new ReservaDto();
        dto.setId(UUID.randomUUID());
        dto.setFecha(LocalDate.of(2026,1,10));
        dto.setHoraInicio(LocalTime.of(10,0));
        dto.setHoraFin(LocalTime.of(11,0));
        dto.setProfesionalId(UUID.randomUUID());
        dto.setClienteId(UUID.randomUUID());

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(clienteRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Cliente.class)));
        when(horarioRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(false));

        assertThrows(BusinessException.class, () -> service.createBooking(dto).await().indefinitely());
    }

    @Test
    public void testCreateBooking_otrosReservas_shouldThrowValidation() {
        ReservaDto dto = new ReservaDto();
        dto.setId(UUID.randomUUID());
        dto.setFecha(LocalDate.of(2026,1,10));
        dto.setHoraInicio(LocalTime.of(10,0));
        dto.setHoraFin(LocalTime.of(11,0));
        dto.setProfesionalId(UUID.randomUUID());
        dto.setClienteId(UUID.randomUUID());

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(clienteRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Cliente.class)));
        when(horarioRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(true));
        when(reservaRepository.validateOtrasReservasProfesional(Mockito.any())).thenReturn(Uni.createFrom().item(true));

        assertThrows(BusinessException.class, () -> service.createBooking(dto).await().indefinitely());
    }

    @Test
    public void testCreateBooking_fechaInvalida_shouldThrowValidation() {
        ReservaDto dto = new ReservaDto();
        dto.setId(UUID.randomUUID());
        dto.setFecha(LocalDate.of(2026,1,10));
        // hora inicio posterior a hora fin
        dto.setHoraInicio(LocalTime.of(12,0));
        dto.setHoraFin(LocalTime.of(11,0));
        dto.setProfesionalId(UUID.randomUUID());
        dto.setClienteId(UUID.randomUUID());

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(clienteRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Cliente.class)));

        assertThrows(BusinessException.class, () -> service.createBooking(dto).await().indefinitely());
    }

    @Test
    public void testUpdateBooking_success() {
        ReservaDto dto = new ReservaDto();
        UUID id = UUID.randomUUID();
        dto.setFecha(LocalDate.of(2026,1,10));
        dto.setHoraInicio(LocalTime.of(9,0));
        dto.setHoraFin(LocalTime.of(10,0));
        dto.setProfesionalId(UUID.randomUUID());
        dto.setClienteId(UUID.randomUUID());

        // stub findByEstado used by findBooking() inside updateBooking
        when(reservaRepository.findByEstado(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(Reserva.class)));

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(clienteRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Cliente.class)));
        when(horarioRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(true));
        when(reservaRepository.validateOtrasReservasProfesional(Mockito.any())).thenReturn(Uni.createFrom().item(false));
        when(reservaRepository.updateReserva(Mockito.any(), Mockito.eq(id))).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> service.updateBooking(dto, id).await().indefinitely());
    }

    @Test
    public void testUpdateBooking_fechaInvalida_shouldThrowValidation() {
        ReservaDto dto = new ReservaDto();
        UUID id = UUID.randomUUID();
        dto.setFecha(LocalDate.of(2026,1,10));
        dto.setHoraInicio(LocalTime.of(11,0));
        dto.setHoraFin(LocalTime.of(10,0));
        dto.setProfesionalId(UUID.randomUUID());
        dto.setClienteId(UUID.randomUUID());

        // stub findByEstado used by findBooking() inside updateBooking
        when(reservaRepository.findByEstado(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(Reserva.class)));

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(clienteRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Cliente.class)));

        assertThrows(BusinessException.class, () -> service.updateBooking(dto, id).await().indefinitely());
    }

    @Test
    public void testDeleteBooking_repositoryFailure_shouldTransformToBusinessException() {
        UUID id = UUID.randomUUID();
        // stub findByEstado used by findBooking() inside deleteBooking
        when(reservaRepository.findByEstado(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(Reserva.class)));
        when(reservaRepository.deleteReserva(Mockito.any())).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        assertThrows(BusinessException.class, () -> service.deleteBooking(id).await().indefinitely());
    }

    @Test
    public void testDeleteBooking_success_shouldReturnWithoutException() {
        UUID id = UUID.randomUUID();
        // stub findByEstado used by findBooking() inside deleteBooking
        when(reservaRepository.findByEstado(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(Reserva.class)));
        when(reservaRepository.deleteReserva(Mockito.any())).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> service.deleteBooking(id).await().indefinitely());
    }

}

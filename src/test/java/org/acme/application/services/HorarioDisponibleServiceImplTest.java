package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.entities.HorarioDisponible;
import org.acme.domain.model.entities.Profesional;
import org.acme.domain.repository.HorarioDisponibleRepository;
import org.acme.domain.repository.ProfesionalRepository;
import org.acme.infraestructure.dtos.HorarioDisponibleDto;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HorarioDisponibleServiceImplTest {

    @Mock
    private HorarioDisponibleRepository horarioDisponibleRepository;

    @Mock
    private ProfesionalRepository profesionalRepository;

    @InjectMocks
    private HorarioDisponibleServiceImpl service;

    @BeforeEach
    public void setup() {
        // Mockito inicializa los mocks e inyecta en 'service'
    }

    // Helper para crear DTO válidos
    private HorarioDisponibleDto createValidDto() {
        HorarioDisponibleDto dto = new HorarioDisponibleDto();
        dto.setProfesionalId(UUID.randomUUID());
        dto.setFecha(LocalDate.of(2026, 1, 10));
        dto.setHoraInicio(LocalTime.of(9, 0));
        dto.setHoraFin(LocalTime.of(10, 0));
        return dto;
    }

    private HorarioDisponibleDto createDtoWithTimes(LocalTime inicio, LocalTime fin) {
        HorarioDisponibleDto dto = createValidDto();
        dto.setHoraInicio(inicio);
        dto.setHoraFin(fin);
        return dto;
    }

    // LIST
    @Test
    public void testListAvailabilityHours_success() {
        // Mock repositorio para devolver al menos una entidad
        HorarioDisponible entidad = Mockito.mock(HorarioDisponible.class);
        when(horarioDisponibleRepository.findAllInactivos()).thenReturn(Multi.createFrom().items(entidad));

        List<HorarioDisponibleDto> result = service.listAvailabilityHours().collect().asList().await().indefinitely();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testListAvailabilityHours_repositoryFailure_shouldTransformToBusinessException() {
        when(horarioDisponibleRepository.findAllInactivos()).thenReturn(Multi.createFrom().failure(new RuntimeException("db error")));

        assertThrows(BusinessException.class, () -> service.listAvailabilityHours().collect().asList().await().indefinitely());
    }

    // FIND
    @Test
    public void testFindAvailabilityHour_success() {
        UUID id = UUID.randomUUID();
        HorarioDisponible entidad = Mockito.mock(HorarioDisponible.class);
        when(horarioDisponibleRepository.findByIdAndInactivo(id)).thenReturn(Uni.createFrom().item(entidad));

        HorarioDisponibleDto dto = service.findAvailabilityHour(id).await().indefinitely();
        assertNotNull(dto);
    }

    @Test
    public void testFindAvailabilityHour_repositoryFailure_shouldTransformToBusinessException() {
        UUID id = UUID.randomUUID();
        when(horarioDisponibleRepository.findByIdAndInactivo(id)).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        assertThrows(BusinessException.class, () -> service.findAvailabilityHour(id).await().indefinitely());
    }

    // CREATE - casos existentes y nuevos
    @Test
    public void testCreateAvailabilityHour_success() {
        HorarioDisponibleDto dto = createValidDto();

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(horarioDisponibleRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(false));
        when(horarioDisponibleRepository.saveHorarioDisponible(Mockito.any())).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> service.createAvailabilityHour(dto).await().indefinitely());
    }

    @Test
    public void testCreateAvailabilityHour_profesionalInactivo_shouldThrowValidation() {
        HorarioDisponibleDto dto = createValidDto();

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item((Profesional) null));
        // evitar NPE en el combine() al no stubbear este método
        when(horarioDisponibleRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(false));

        assertThrows(BusinessException.class, () -> service.createAvailabilityHour(dto).await().indefinitely());
    }

    @Test
    public void testCreateAvailabilityHour_startEqualsEnd_shouldThrowValidationError() {
        HorarioDisponibleDto dto = createDtoWithTimes(LocalTime.of(9, 0), LocalTime.of(9, 0));

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        // asegurar que la comprobación de existencia de horario está stubbeada
        when(horarioDisponibleRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(false));

        assertThrows(BusinessException.class, () -> service.createAvailabilityHour(dto).await().indefinitely());
    }

    @Test
    public void testCreateAvailabilityHour_startAfterEnd_shouldThrowValidationError() {
        HorarioDisponibleDto dto = createDtoWithTimes(LocalTime.of(11, 0), LocalTime.of(10, 0));

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(horarioDisponibleRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(false));

        assertThrows(BusinessException.class, () -> service.createAvailabilityHour(dto).await().indefinitely());
    }

    @Test
    public void testCreateAvailabilityHour_existingHorario_shouldThrowValidationError() {
        HorarioDisponibleDto dto = createValidDto();

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(horarioDisponibleRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(true));

        assertThrows(BusinessException.class, () -> service.createAvailabilityHour(dto).await().indefinitely());
    }

    @Test
    public void testCreateAvailabilityHour_repositoryFailure_shouldTransformToBusinessException() {
        HorarioDisponibleDto dto = createValidDto();

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(horarioDisponibleRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(false));
        when(horarioDisponibleRepository.saveHorarioDisponible(Mockito.any())).thenReturn(Uni.createFrom().failure(new RuntimeException("db error")));

        assertThrows(BusinessException.class, () -> service.createAvailabilityHour(dto).await().indefinitely());
    }

    @Test
    public void testCreateAvailabilityHour_nullHoraFields_shouldThrowNPE() {
        HorarioDisponibleDto dto = createValidDto();
        dto.setHoraInicio(null);
        dto.setHoraFin(null);

        assertThrows(NullPointerException.class, () -> service.createAvailabilityHour(dto).await().indefinitely());
    }

    // UPDATE
    @Test
    public void testUpdateAvailabilityHour_success() {
        HorarioDisponibleDto dto = createValidDto();
        UUID id = UUID.randomUUID();

        // stub repo findById to avoid NOT_FOUND
        when(horarioDisponibleRepository.findByIdAndInactivo(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(HorarioDisponible.class)));

        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(horarioDisponibleRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(false));
        when(horarioDisponibleRepository.updateHorarioDisponible(Mockito.any(), Mockito.eq(id))).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> service.updateAvailabilityHour(dto, id).await().indefinitely());
    }

    @Test
    public void testUpdateAvailabilityHour_fechaInvalida_shouldThrowValidation() {
        HorarioDisponibleDto dto = createDtoWithTimes(LocalTime.of(12, 0), LocalTime.of(11, 0));
        UUID id = UUID.randomUUID();

        when(horarioDisponibleRepository.findByIdAndInactivo(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(HorarioDisponible.class)));
        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        // evitar NPE en combine
        when(horarioDisponibleRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(false));

        assertThrows(BusinessException.class, () -> service.updateAvailabilityHour(dto, id).await().indefinitely());
    }

    @Test
    public void testUpdateAvailabilityHour_repositoryFailure_shouldTransformToBusinessException() {
        HorarioDisponibleDto dto = createValidDto();
        UUID id = UUID.randomUUID();

        when(horarioDisponibleRepository.findByIdAndInactivo(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(HorarioDisponible.class)));
        when(profesionalRepository.findByIdAndInactivo(Mockito.any())).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(horarioDisponibleRepository.validateHorarioDisponibleHours(Mockito.any())).thenReturn(Uni.createFrom().item(false));
        when(horarioDisponibleRepository.updateHorarioDisponible(Mockito.any(), Mockito.eq(id))).thenReturn(Uni.createFrom().failure(new RuntimeException("db error")));

        assertThrows(BusinessException.class, () -> service.updateAvailabilityHour(dto, id).await().indefinitely());
    }

    // DELETE
    @Test
    public void testDeleteAvailabilityHour_success_shouldReturnWithoutException() {
        UUID id = UUID.randomUUID();
        when(horarioDisponibleRepository.findByIdAndInactivo(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(HorarioDisponible.class)));
        when(horarioDisponibleRepository.deleteHorarioDisponible(Mockito.any())).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> service.deleteAvailabilityHour(id).await().indefinitely());
    }

    @Test
    public void testDeleteAvailabilityHour_repositoryFailure_shouldTransformToBusinessException() {
        UUID id = UUID.randomUUID();
        when(horarioDisponibleRepository.findByIdAndInactivo(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(HorarioDisponible.class)));
        when(horarioDisponibleRepository.deleteHorarioDisponible(Mockito.any())).thenReturn(Uni.createFrom().failure(new RuntimeException("db error")));

        assertThrows(BusinessException.class, () -> service.deleteAvailabilityHour(id).await().indefinitely());
    }
}

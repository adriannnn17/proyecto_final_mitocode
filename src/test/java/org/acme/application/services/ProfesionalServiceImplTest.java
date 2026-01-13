package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.entities.Profesional;
import org.acme.domain.repository.ProfesionalRepository;
import org.acme.infraestructure.dtos.ProfesionalDto;
import org.acme.infraestructure.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfesionalServiceImplTest {

    @Mock
    private ProfesionalRepository profesionalRepository;

    @InjectMocks
    private ProfesionalServiceImpl service;

    @Test
    public void testListProfessionals_success() {
        when(profesionalRepository.findAllInactivos()).thenReturn(Multi.createFrom().item(Mockito.mock(Profesional.class)));

        assertDoesNotThrow(() -> {
            var res = service.listProfessionals().collect().asList().await().indefinitely();
            assertNotNull(res);
            assertFalse(res.isEmpty());
        });
    }

    @Test
    public void testListProfessionals_repositoryFailure_shouldTransformToBusinessException() {
        when(profesionalRepository.findAllInactivos()).thenReturn(Multi.createFrom().failure(new RuntimeException("db")));

        assertThrows(BusinessException.class, () -> service.listProfessionals().collect().asList().await().indefinitely());
    }

    @Test
    public void testFindProfessional_success() {
        UUID id = UUID.randomUUID();
        when(profesionalRepository.findByIdAndInactivo(id)).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));

        assertDoesNotThrow(() -> {
            var res = service.findProfessional(id).await().indefinitely();
            assertNotNull(res);
        });
    }

    @Test
    public void testFindProfessional_repositoryFailure_shouldTransformToBusinessException() {
        UUID id = UUID.randomUUID();
        when(profesionalRepository.findByIdAndInactivo(id)).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        assertThrows(BusinessException.class, () -> service.findProfessional(id).await().indefinitely());
    }

    @Test
    public void testCreateProfessional_success() {
        ProfesionalDto dto = new ProfesionalDto();
        dto.setId(UUID.randomUUID());

        when(profesionalRepository.saveProfesional(Mockito.any())).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> service.createProfessional(dto).await().indefinitely());
    }

    @Test
    public void testCreateProfessional_repositoryFailure_shouldTransformToBusinessException() {
        ProfesionalDto dto = new ProfesionalDto();
        dto.setId(UUID.randomUUID());

        when(profesionalRepository.saveProfesional(Mockito.any())).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        assertThrows(BusinessException.class, () -> service.createProfessional(dto).await().indefinitely());
    }

    @Test
    public void testUpdateProfessional_success() {
        ProfesionalDto dto = new ProfesionalDto();
        dto.setId(UUID.randomUUID());
        UUID id = UUID.randomUUID();

        // stub repository find used by findProfessional inside updateProfessional
        when(profesionalRepository.findByIdAndInactivo(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));

        when(profesionalRepository.updateProfesional(Mockito.any(), Mockito.eq(id))).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> service.updateProfessional(dto, id).await().indefinitely());
    }

    @Test
    public void testUpdateProfessional_repositoryFailure_shouldTransformToBusinessException() {
        ProfesionalDto dto = new ProfesionalDto();
        dto.setId(UUID.randomUUID());
        UUID id = UUID.randomUUID();

        // ensure findProfessional succeeds so updateProfessional reaches repository failure
        when(profesionalRepository.findByIdAndInactivo(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));

        when(profesionalRepository.updateProfesional(Mockito.any(), Mockito.eq(id))).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        assertThrows(BusinessException.class, () -> service.updateProfessional(dto, id).await().indefinitely());
    }

    @Test
    public void testDeleteProfessional_success() {
        UUID id = UUID.randomUUID();
        // stub find to allow delete flow
        when(profesionalRepository.findByIdAndInactivo(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(profesionalRepository.deleteProfesional(id)).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> service.deleteProfessional(id).await().indefinitely());
    }

    @Test
    public void testDeleteProfessional_repositoryFailure_shouldTransformToBusinessException() {
        UUID id = UUID.randomUUID();
        when(profesionalRepository.findByIdAndInactivo(Mockito.eq(id))).thenReturn(Uni.createFrom().item(Mockito.mock(Profesional.class)));
        when(profesionalRepository.deleteProfesional(id)).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        assertThrows(BusinessException.class, () -> service.deleteProfessional(id).await().indefinitely());
    }
}

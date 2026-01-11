package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.entities.Cliente;
import org.acme.domain.repository.ClienteRepository;
import org.acme.infraestructure.dtos.ClienteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    public void setup() {
        // Mockito inicializa mocks e inyecta en clienteService
    }

    @Test
    public void testCreateClient_success() {
        ClienteDto dto = new ClienteDto();
        dto.setId(UUID.randomUUID());
        dto.setNombres("Test");
        dto.setApellidos("User");

        when(clienteRepository.saveClient(Mockito.any())).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> clienteService.createClient(dto).await().indefinitely());
    }

    @Test
    public void testCreateClient_repositoryFailure_shouldTransformToBusinessException() {
        ClienteDto dto = new ClienteDto();
        dto.setId(UUID.randomUUID());

        when(clienteRepository.saveClient(Mockito.any())).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        assertThrows(Exception.class, () -> clienteService.createClient(dto).await().indefinitely());
    }

    @Test
    public void testFindClient_success_returnsNullWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findByIdAndInactivo(id)).thenReturn(Uni.createFrom().item((Cliente) null));

        assertDoesNotThrow(() -> clienteService.findClient(id).await().indefinitely());
    }

    @Test
    public void testListClients_empty() {
        when(clienteRepository.findAllInactivos()).thenReturn(Multi.createFrom().empty());

        assertDoesNotThrow(() -> {
            var result = clienteService.listClients().collect().asList().await().indefinitely();
            assertNotNull(result);
            assertTrue(result.isEmpty());
        });
    }

    @Test
    public void testUpdateClient_success() {
        ClienteDto dto = new ClienteDto();
        dto.setId(UUID.randomUUID());

        UUID id = UUID.randomUUID();
        when(clienteRepository.updateClient(Mockito.any(), Mockito.eq(id))).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> clienteService.updateClient(dto, id).await().indefinitely());
    }

    @Test
    public void testUpdateClient_repositoryFailure_shouldTransformToBusinessException() {
        ClienteDto dto = new ClienteDto();
        UUID id = UUID.randomUUID();

        when(clienteRepository.updateClient(Mockito.any(), Mockito.eq(id))).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        assertThrows(Exception.class, () -> clienteService.updateClient(dto, id).await().indefinitely());
    }

    @Test
    public void testDeleteClient_success() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.deleteClient(id)).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> clienteService.deleteClient(id).await().indefinitely());
    }

    @Test
    public void testDeleteClient_repositoryFailure_shouldTransformToBusinessException() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.deleteClient(id)).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        assertThrows(Exception.class, () -> clienteService.deleteClient(id).await().indefinitely());
    }
}

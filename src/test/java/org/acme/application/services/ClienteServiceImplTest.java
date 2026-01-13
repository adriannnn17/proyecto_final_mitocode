package org.acme.application.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.entities.Cliente;
import org.acme.domain.repository.ClienteRepository;
import org.acme.infraestructure.dtos.ClienteDto;
import org.acme.infraestructure.exceptions.BusinessErrorType;
import org.acme.infraestructure.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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

        BusinessException ex = assertThrows(BusinessException.class, () -> clienteService.createClient(dto).await().indefinitely());
        assertEquals(BusinessErrorType.PERSISTENCE_ERROR, ex.getType());
        assertTrue(ex.getDescription().contains("db"));
    }

    @Test
    public void testFindClient_notFound_shouldThrowNotFound() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findByIdAndInactivo(id)).thenReturn(Uni.createFrom().item((Cliente) null));

        BusinessException ex = assertThrows(BusinessException.class, () -> clienteService.findClient(id).await().indefinitely());
        assertEquals(BusinessErrorType.NOT_FOUND, ex.getType());
        assertTrue(ex.getDescription().contains(id.toString()));
    }

    @Test
    public void testFindClient_success() {
        UUID id = UUID.randomUUID();
        Cliente mocked = Mockito.mock(Cliente.class);
        when(clienteRepository.findByIdAndInactivo(id)).thenReturn(Uni.createFrom().item(mocked));

        ClienteDto result = assertDoesNotThrow(() -> clienteService.findClient(id).await().indefinitely());
        assertNotNull(result);
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
    public void testListClients_withItems() {
        Cliente mocked = Mockito.mock(Cliente.class);
        when(clienteRepository.findAllInactivos()).thenReturn(Multi.createFrom().items(mocked));

        List<ClienteDto> result = clienteService.listClients().collect().asList().await().indefinitely();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdateClient_success() {
        ClienteDto dto = new ClienteDto();
        dto.setId(UUID.randomUUID());

        UUID id = UUID.randomUUID();
        // Stub findClient at service level using a spy to avoid mapping issues
        ClienteDto existingDto = new ClienteDto();
        existingDto.setId(id);

        ClienteServiceImpl spyService = Mockito.spy(clienteService);
        Mockito.doReturn(Uni.createFrom().item(existingDto)).when(spyService).findClient(Mockito.eq(id));

        when(clienteRepository.updateClient(Mockito.any(), Mockito.eq(id))).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> spyService.updateClient(dto, id).await().indefinitely());
    }

    @Test
    public void testUpdateClient_repositoryFailure_shouldTransformToBusinessException() {
        ClienteDto dto = new ClienteDto();
        UUID id = UUID.randomUUID();

        // stub findClient via spy
        ClienteDto existingDto = new ClienteDto();
        existingDto.setId(id);
        ClienteServiceImpl spyService = Mockito.spy(clienteService);
        Mockito.doReturn(Uni.createFrom().item(existingDto)).when(spyService).findClient(Mockito.eq(id));

        when(clienteRepository.updateClient(Mockito.any(), Mockito.eq(id))).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        BusinessException ex = assertThrows(BusinessException.class, () -> spyService.updateClient(dto, id).await().indefinitely());
        assertEquals(BusinessErrorType.PERSISTENCE_ERROR, ex.getType());
    }

    @Test
    public void testDeleteClient_success() {
        UUID id = UUID.randomUUID();
        // stub findClient via spy
        ClienteDto existingDto = new ClienteDto();
        existingDto.setId(id);
        ClienteServiceImpl spyService = Mockito.spy(clienteService);
        Mockito.doReturn(Uni.createFrom().item(existingDto)).when(spyService).findClient(Mockito.eq(id));

        when(clienteRepository.deleteClient(id)).thenReturn(Uni.createFrom().voidItem());

        assertDoesNotThrow(() -> spyService.deleteClient(id).await().indefinitely());
    }

    @Test
    public void testDeleteClient_repositoryFailure_shouldTransformToBusinessException() {
        UUID id = UUID.randomUUID();
        ClienteDto existingDto = new ClienteDto();
        existingDto.setId(id);
        ClienteServiceImpl spyService = Mockito.spy(clienteService);
        Mockito.doReturn(Uni.createFrom().item(existingDto)).when(spyService).findClient(Mockito.eq(id));

        when(clienteRepository.deleteClient(id)).thenReturn(Uni.createFrom().failure(new RuntimeException("db")));

        BusinessException ex = assertThrows(BusinessException.class, () -> spyService.deleteClient(id).await().indefinitely());
        assertEquals(BusinessErrorType.PERSISTENCE_ERROR, ex.getType());
    }
}

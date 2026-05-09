package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.BusinessException;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private IClienteRepository repository;

    @Mock
    private IClienteValidation validation;

    private ClienteModel clienteModel;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        org.springframework.test.util.ReflectionTestUtils.setField(clienteService, "repository", repository);
        org.springframework.test.util.ReflectionTestUtils.setField(clienteService, "validation", validation);
        
        clienteId = UUID.randomUUID();
        clienteModel = new ClienteModel();
        clienteModel.setId(clienteId);
        clienteModel.setNome("João da Silva");
        clienteModel.setCpf("12345678901");
        clienteModel.setAtivo(true);
    }

    @Test
    void testFindByIdActive_Success() {
        when(repository.findByIdAndAtivoTrue(clienteId)).thenReturn(Optional.of(clienteModel));

        ClienteModel result = clienteService.findByIdActive(clienteId);

        assertNotNull(result);
        assertEquals("João da Silva", result.getNome());
        verify(repository, times(1)).findByIdAndAtivoTrue(clienteId);
    }

    @Test
    void testFindByIdActive_NotFound() {
        when(repository.findByIdAndAtivoTrue(clienteId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> clienteService.findByIdActive(clienteId));
        verify(repository, times(1)).findByIdAndAtivoTrue(clienteId);
    }

    @Test
    void testFindAllActive_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ClienteModel> page = new PageImpl<>(List.of(clienteModel));
        when(repository.findAllByAtivoTrue(pageable)).thenReturn(page);

        Page<ClienteModel> result = clienteService.findAllActive(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(repository, times(1)).findAllByAtivoTrue(pageable);
    }

    @Test
    void testInsert_Success() {
        when(repository.save(any(ClienteModel.class))).thenReturn(clienteModel);
        
        doNothing().when(validation).validateFieldsInsert(any(ClienteModel.class));
        doNothing().when(validation).validateInsert(any(ClienteModel.class));

        ClienteModel result = clienteService.insert(clienteModel);

        assertNotNull(result);
        assertEquals("João da Silva", result.getNome());
        verify(validation, times(1)).validateFieldsInsert(clienteModel);
        verify(validation, times(1)).validateInsert(clienteModel);
        verify(repository, times(1)).save(clienteModel);
    }

    @Test
    void testUpdate_Success() {
        when(repository.save(any(ClienteModel.class))).thenReturn(clienteModel);

        doNothing().when(validation).validateFieldsUpdate(any(ClienteModel.class));
        doNothing().when(validation).validateUpdate(any(ClienteModel.class));

        ClienteModel result = clienteService.update(clienteModel);

        assertNotNull(result);
        verify(validation, times(1)).validateFieldsUpdate(clienteModel);
        verify(validation, times(1)).validateUpdate(clienteModel);
        verify(repository, times(1)).save(clienteModel);
    }

    @Test
    void testDelete_Success() {
        when(repository.findByIdAndAtivoTrue(clienteId)).thenReturn(Optional.of(clienteModel));
        doNothing().when(validation).validateDelete(clienteId);

        clienteService.delete(clienteId);

        verify(validation, times(1)).validateDelete(clienteId);
        verify(repository, times(1)).findByIdAndAtivoTrue(clienteId);
        verify(repository, times(1)).save(clienteModel);
        assertFalse(clienteModel.isAtivo()); // Deve ter ficado false (soft delete)
    }
}

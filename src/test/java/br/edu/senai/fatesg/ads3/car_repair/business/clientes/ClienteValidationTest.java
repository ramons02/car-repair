package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClienteValidationTest {

    @InjectMocks
    private ClienteValidation clienteValidation;

    @Mock
    private IClienteRepository repository;

    private ClienteModel clienteModel;

    @BeforeEach
    void setUp() {
        clienteModel = new ClienteModel();
        clienteModel.setId(UUID.randomUUID());
        clienteModel.setNome("Maria da Silva");
        clienteModel.setCpf("09876543210");
    }

    @Test
    void testValidateInsert_Success() {
        assertDoesNotThrow(() -> clienteValidation.validateInsert(clienteModel));
    }

    @Test
    void testValidateUpdate_Success() {
        assertDoesNotThrow(() -> clienteValidation.validateUpdate(clienteModel));
    }
}

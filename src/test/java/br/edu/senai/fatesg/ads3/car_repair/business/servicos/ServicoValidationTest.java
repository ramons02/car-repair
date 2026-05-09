package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServicoValidationTest {

    @InjectMocks
    private ServicoValidation servicoValidation;

    @Mock
    private IServicoRepository repository;

    private ServicoModel servicoModel;

    @BeforeEach
    void setUp() {
        servicoModel = new ServicoModel();
        servicoModel.setId(UUID.randomUUID());
    }

    @Test
    void testValidateInsert_Success() {
        assertDoesNotThrow(() -> servicoValidation.validateInsert(servicoModel));
    }

    @Test
    void testValidateUpdate_Success() {
        assertDoesNotThrow(() -> servicoValidation.validateUpdate(servicoModel));
    }
}

package br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrdemServicoValidationTest {

    @InjectMocks
    private OrdemServicoValidation ordemServicoValidation;

    @Mock
    private IOrdemServicoRepository repository;

    private OrdemServicoModel ordemServicoModel;

    @BeforeEach
    void setUp() {
        ordemServicoModel = new OrdemServicoModel();
        ordemServicoModel.setId(UUID.randomUUID());
    }

    @Test
    void testValidateInsert_Success() {
        assertDoesNotThrow(() -> ordemServicoValidation.validateInsert(ordemServicoModel));
    }

    @Test
    void testValidateUpdate_Success() {
        assertDoesNotThrow(() -> ordemServicoValidation.validateUpdate(ordemServicoModel));
    }
}

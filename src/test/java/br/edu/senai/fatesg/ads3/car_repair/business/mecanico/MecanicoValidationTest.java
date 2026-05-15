package br.edu.senai.fatesg.ads3.car_repair.business.mecanico;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MecanicoValidationTest {

    @InjectMocks
    private MecanicoValidation mecanicoValidation;

    @Mock
    private IMecanicoRepository repository;

    private MecanicoModel mecanicoModel;

    @BeforeEach
    void setUp() {
        mecanicoModel = new MecanicoModel();
        mecanicoModel.setId(UUID.randomUUID());
        mecanicoModel.setNome("João Mecânico");
    }

    @Test
    void testValidateInsert_Success() {
        assertDoesNotThrow(() -> mecanicoValidation.validateInsert(mecanicoModel));
    }

    @Test
    void testValidateUpdate_Success() {
        assertDoesNotThrow(() -> mecanicoValidation.validateUpdate(mecanicoModel));
    }
}

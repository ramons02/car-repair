package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VeiculoValidationTest {

    @InjectMocks
    private VeiculoValidation veiculoValidation;

    @Mock
    private IVeiculoRepository repository;

    private VeiculoModel veiculoModel;

    @BeforeEach
    void setUp() {
        veiculoModel = new VeiculoModel();
        veiculoModel.setId(UUID.randomUUID());
    }

    @Test
    void testValidateInsert_Success() {
        assertDoesNotThrow(() -> veiculoValidation.validateInsert(veiculoModel));
    }

    @Test
    void testValidateUpdate_Success() {
        assertDoesNotThrow(() -> veiculoValidation.validateUpdate(veiculoModel));
    }
}

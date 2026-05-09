package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

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
import org.springframework.test.util.ReflectionTestUtils;

import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.BusinessException;

@ExtendWith(MockitoExtension.class)
public class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService veiculoService;

    @Mock
    private IVeiculoRepository repository;

    @Mock
    private IVeiculoValidation validation;

    private VeiculoModel veiculoModel;
    private UUID veiculoId;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(veiculoService, "repository", repository);
        ReflectionTestUtils.setField(veiculoService, "validation", validation);
        
        veiculoId = UUID.randomUUID();
        veiculoModel = new VeiculoModel();
        veiculoModel.setId(veiculoId);
        veiculoModel.setMarca("Toyota");
        veiculoModel.setModelo("Corolla");
        veiculoModel.setAtivo(true);
    }

    @Test
    void testFindByIdActive_Success() {
        when(repository.findByIdAndAtivoTrue(veiculoId)).thenReturn(Optional.of(veiculoModel));
        VeiculoModel result = veiculoService.findByIdActive(veiculoId);
        assertNotNull(result);
        assertEquals("Toyota", result.getMarca());
        verify(repository, times(1)).findByIdAndAtivoTrue(veiculoId);
    }

    @Test
    void testFindByIdActive_NotFound() {
        when(repository.findByIdAndAtivoTrue(veiculoId)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> veiculoService.findByIdActive(veiculoId));
    }

    @Test
    void testFindAllActive_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<VeiculoModel> page = new PageImpl<>(List.of(veiculoModel));
        when(repository.findAllByAtivoTrue(pageable)).thenReturn(page);
        Page<VeiculoModel> result = veiculoService.findAllActive(pageable);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testInsert_Success() {
        when(repository.save(any(VeiculoModel.class))).thenReturn(veiculoModel);
        VeiculoModel result = veiculoService.insert(veiculoModel);
        assertNotNull(result);
        verify(validation, times(1)).validateFieldsInsert(veiculoModel);
        verify(validation, times(1)).validateInsert(veiculoModel);
        verify(repository, times(1)).save(veiculoModel);
    }

    @Test
    void testUpdate_Success() {
        when(repository.save(any(VeiculoModel.class))).thenReturn(veiculoModel);
        VeiculoModel result = veiculoService.update(veiculoModel);
        assertNotNull(result);
        verify(validation, times(1)).validateFieldsUpdate(veiculoModel);
        verify(validation, times(1)).validateUpdate(veiculoModel);
        verify(repository, times(1)).save(veiculoModel);
    }

    @Test
    void testDelete_Success() {
        when(repository.findByIdAndAtivoTrue(veiculoId)).thenReturn(Optional.of(veiculoModel));
        veiculoService.delete(veiculoId);
        verify(validation, times(1)).validateDelete(veiculoId);
        verify(repository, times(1)).save(veiculoModel);
        assertFalse(veiculoModel.isAtivo());
    }
}

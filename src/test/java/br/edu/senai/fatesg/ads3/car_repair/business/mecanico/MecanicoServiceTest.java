package br.edu.senai.fatesg.ads3.car_repair.business.mecanico;

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
public class MecanicoServiceTest {

    @InjectMocks
    private MecanicoService mecanicoService;

    @Mock
    private IMecanicoRepository repository;

    @Mock
    private IMecanicoValidation validation;

    private MecanicoModel mecanicoModel;
    private UUID mecanicoId;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mecanicoService, "repository", repository);
        ReflectionTestUtils.setField(mecanicoService, "validation", validation);
        
        mecanicoId = UUID.randomUUID();
        mecanicoModel = new MecanicoModel();
        mecanicoModel.setId(mecanicoId);
        mecanicoModel.setNome("João Mecânico");
        mecanicoModel.setEspecialidade("Motores");
        mecanicoModel.setTelefone("62999999999");
        mecanicoModel.setAtivo(true);
    }

    @Test
    void testFindByIdActive_Success() {
        when(repository.findByIdAndAtivoTrue(mecanicoId)).thenReturn(Optional.of(mecanicoModel));
        MecanicoModel result = mecanicoService.findByIdActive(mecanicoId);
        assertNotNull(result);
        assertEquals("João Mecânico", result.getNome());
        verify(repository, times(1)).findByIdAndAtivoTrue(mecanicoId);
    }

    @Test
    void testFindByIdActive_NotFound() {
        when(repository.findByIdAndAtivoTrue(mecanicoId)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> mecanicoService.findByIdActive(mecanicoId));
    }

    @Test
    void testFindAllActive_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<MecanicoModel> page = new PageImpl<>(List.of(mecanicoModel));
        when(repository.findAllByAtivoTrue(pageable)).thenReturn(page);
        Page<MecanicoModel> result = mecanicoService.findAllActive(pageable);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testInsert_Success() {
        when(repository.save(any(MecanicoModel.class))).thenReturn(mecanicoModel);
        MecanicoModel result = mecanicoService.insert(mecanicoModel);
        assertNotNull(result);
        verify(validation, times(1)).validateFieldsInsert(mecanicoModel);
        verify(validation, times(1)).validateInsert(mecanicoModel);
        verify(repository, times(1)).save(mecanicoModel);
    }

    @Test
    void testUpdate_Success() {
        when(repository.save(any(MecanicoModel.class))).thenReturn(mecanicoModel);
        MecanicoModel result = mecanicoService.update(mecanicoModel);
        assertNotNull(result);
        verify(validation, times(1)).validateFieldsUpdate(mecanicoModel);
        verify(validation, times(1)).validateUpdate(mecanicoModel);
        verify(repository, times(1)).save(mecanicoModel);
    }

    @Test
    void testDelete_Success() {
        when(repository.findByIdAndAtivoTrue(mecanicoId)).thenReturn(Optional.of(mecanicoModel));
        mecanicoService.delete(mecanicoId);
        verify(validation, times(1)).validateDelete(mecanicoId);
        verify(repository, times(1)).save(mecanicoModel);
        assertFalse(mecanicoModel.isAtivo());
    }
}

package br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos;

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
public class OrdemServicoServiceTest {

    @InjectMocks
    private OrdemServicoService ordemServicoService;

    @Mock
    private IOrdemServicoRepository repository;

    @Mock
    private IOrdemServicoValidation validation;

    private OrdemServicoModel ordemServicoModel;
    private UUID ordemServicoId;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ordemServicoService, "repository", repository);
        ReflectionTestUtils.setField(ordemServicoService, "validation", validation);
        
        ordemServicoId = UUID.randomUUID();
        ordemServicoModel = new OrdemServicoModel();
        ordemServicoModel.setId(ordemServicoId);
        ordemServicoModel.setProblema("Motor vazando");
        ordemServicoModel.setAtivo(true);
    }

    @Test
    void testFindByIdActive_Success() {
        when(repository.findByIdAndAtivoTrue(ordemServicoId)).thenReturn(Optional.of(ordemServicoModel));
        OrdemServicoModel result = ordemServicoService.findByIdActive(ordemServicoId);
        assertNotNull(result);
        assertEquals("Motor vazando", result.getProblema());
        verify(repository, times(1)).findByIdAndAtivoTrue(ordemServicoId);
    }

    @Test
    void testFindByIdActive_NotFound() {
        when(repository.findByIdAndAtivoTrue(ordemServicoId)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> ordemServicoService.findByIdActive(ordemServicoId));
    }

    @Test
    void testFindAllActive_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrdemServicoModel> page = new PageImpl<>(List.of(ordemServicoModel));
        when(repository.findAllByAtivoTrue(pageable)).thenReturn(page);
        Page<OrdemServicoModel> result = ordemServicoService.findAllActive(pageable);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testInsert_Success() {
        when(repository.save(any(OrdemServicoModel.class))).thenReturn(ordemServicoModel);
        OrdemServicoModel result = ordemServicoService.insert(ordemServicoModel);
        assertNotNull(result);
        verify(validation, times(1)).validateFieldsInsert(ordemServicoModel);
        verify(validation, times(1)).validateInsert(ordemServicoModel);
        verify(repository, times(1)).save(ordemServicoModel);
    }

    @Test
    void testUpdate_Success() {
        when(repository.save(any(OrdemServicoModel.class))).thenReturn(ordemServicoModel);
        OrdemServicoModel result = ordemServicoService.update(ordemServicoModel);
        assertNotNull(result);
        verify(validation, times(1)).validateFieldsUpdate(ordemServicoModel);
        verify(validation, times(1)).validateUpdate(ordemServicoModel);
        verify(repository, times(1)).save(ordemServicoModel);
    }

    @Test
    void testDelete_Success() {
        when(repository.findByIdAndAtivoTrue(ordemServicoId)).thenReturn(Optional.of(ordemServicoModel));
        ordemServicoService.delete(ordemServicoId);
        verify(validation, times(1)).validateDelete(ordemServicoId);
        verify(repository, times(1)).save(ordemServicoModel);
        assertFalse(ordemServicoModel.isAtivo());
    }
}

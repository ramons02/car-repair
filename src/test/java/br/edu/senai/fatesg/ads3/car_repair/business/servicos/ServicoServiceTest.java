package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

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
public class ServicoServiceTest {

    @InjectMocks
    private ServicoService servicoService;

    @Mock
    private IServicoRepository repository;

    @Mock
    private IServicoValidation validation;

    private ServicoModel servicoModel;
    private UUID servicoId;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(servicoService, "repository", repository);
        ReflectionTestUtils.setField(servicoService, "validation", validation);
        
        servicoId = UUID.randomUUID();
        servicoModel = new ServicoModel();
        servicoModel.setId(servicoId);
        servicoModel.setDescricao("Troca de Óleo");
        
        br.edu.senai.fatesg.ads3.car_repair.business.mecanico.MecanicoModel mecanico = new br.edu.senai.fatesg.ads3.car_repair.business.mecanico.MecanicoModel();
        mecanico.setId(UUID.randomUUID());
        mecanico.setNome("João Mecânico");
        servicoModel.setMecanico(mecanico);
        
        servicoModel.setAtivo(true);
    }

    @Test
    void testFindByIdActive_Success() {
        when(repository.findByIdAndAtivoTrue(servicoId)).thenReturn(Optional.of(servicoModel));
        ServicoModel result = servicoService.findByIdActive(servicoId);
        assertNotNull(result);
        assertEquals("Troca de Óleo", result.getDescricao());
        verify(repository, times(1)).findByIdAndAtivoTrue(servicoId);
    }

    @Test
    void testFindByIdActive_NotFound() {
        when(repository.findByIdAndAtivoTrue(servicoId)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> servicoService.findByIdActive(servicoId));
    }

    @Test
    void testFindAllActive_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ServicoModel> page = new PageImpl<>(List.of(servicoModel));
        when(repository.findAllByAtivoTrue(pageable)).thenReturn(page);
        Page<ServicoModel> result = servicoService.findAllActive(pageable);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testInsert_Success() {
        when(repository.save(any(ServicoModel.class))).thenReturn(servicoModel);
        ServicoModel result = servicoService.insert(servicoModel);
        assertNotNull(result);
        verify(validation, times(1)).validateFieldsInsert(servicoModel);
        verify(validation, times(1)).validateInsert(servicoModel);
        verify(repository, times(1)).save(servicoModel);
    }

    @Test
    void testUpdate_Success() {
        when(repository.save(any(ServicoModel.class))).thenReturn(servicoModel);
        ServicoModel result = servicoService.update(servicoModel);
        assertNotNull(result);
        verify(validation, times(1)).validateFieldsUpdate(servicoModel);
        verify(validation, times(1)).validateUpdate(servicoModel);
        verify(repository, times(1)).save(servicoModel);
    }

    @Test
    void testDelete_Success() {
        when(repository.findByIdAndAtivoTrue(servicoId)).thenReturn(Optional.of(servicoModel));
        servicoService.delete(servicoId);
        verify(validation, times(1)).validateDelete(servicoId);
        verify(repository, times(1)).save(servicoModel);
        assertFalse(servicoModel.isAtivo());
    }
}

package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ServicoController.class)
public class ServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IServicoService service;

    @MockBean
    private ServicoMapper mapper;

    private ServicoModel servicoModel;
    private ServicoDTO servicoDTO;
    private UUID servicoId;

    @BeforeEach
    void setUp() {
        servicoId = UUID.randomUUID();
        servicoModel = new ServicoModel();
        servicoModel.setId(servicoId);
        servicoModel.setDescricao("Troca de Óleo");

        servicoDTO = new ServicoDTO();
        servicoDTO.setId(servicoId);
        servicoDTO.setDescricao("Troca de Óleo");
    }

    @Test
    void testGetById() throws Exception {
        when(service.findByIdActive(servicoId)).thenReturn(servicoModel);
        when(mapper.toDto(servicoModel)).thenReturn(servicoDTO);

        mockMvc.perform(get("/servicos/{id}", servicoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Troca de Óleo"));
    }

    @Test
    void testInsert() throws Exception {
        when(mapper.toEntity(any(ServicoDTO.class))).thenReturn(servicoModel);
        when(service.insert(any(ServicoModel.class))).thenReturn(servicoModel);
        when(mapper.toDto(any(ServicoModel.class))).thenReturn(servicoDTO);

        mockMvc.perform(post("/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servicoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        when(mapper.toEntity(any(ServicoDTO.class))).thenReturn(servicoModel);
        when(service.update(any(ServicoModel.class))).thenReturn(servicoModel);
        when(mapper.toDto(any(ServicoModel.class))).thenReturn(servicoDTO);

        mockMvc.perform(put("/servicos/{id}", servicoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servicoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(servicoId);
        mockMvc.perform(delete("/servicos/{id}", servicoId))
                .andExpect(status().isNoContent());
    }
}

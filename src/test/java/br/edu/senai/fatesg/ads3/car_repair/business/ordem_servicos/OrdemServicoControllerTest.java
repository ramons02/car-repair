package br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos;

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

@WebMvcTest(OrdemServicoController.class)
public class OrdemServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IOrdemServicoService service;

    @MockBean
    private OrdemServicoMapper mapper;

    private OrdemServicoModel ordemServicoModel;
    private OrdemServicoDTO ordemServicoDTO;
    private UUID ordemServicoId;

    @BeforeEach
    void setUp() {
        ordemServicoId = UUID.randomUUID();
        ordemServicoModel = new OrdemServicoModel();
        ordemServicoModel.setId(ordemServicoId);
        ordemServicoModel.setProblema("Motor vazando");

        ordemServicoDTO = new OrdemServicoDTO();
        ordemServicoDTO.setId(ordemServicoId);
        ordemServicoDTO.setProblema("Motor vazando");
    }

    @Test
    void testGetById() throws Exception {
        when(service.findByIdActive(ordemServicoId)).thenReturn(ordemServicoModel);
        when(mapper.toDto(ordemServicoModel)).thenReturn(ordemServicoDTO);

        mockMvc.perform(get("/ordem-servicos/{id}", ordemServicoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.problema").value("Motor vazando"));
    }

    @Test
    void testInsert() throws Exception {
        when(mapper.toEntity(any(OrdemServicoDTO.class))).thenReturn(ordemServicoModel);
        when(service.insert(any(OrdemServicoModel.class))).thenReturn(ordemServicoModel);
        when(mapper.toDto(any(OrdemServicoModel.class))).thenReturn(ordemServicoDTO);

        mockMvc.perform(post("/ordem-servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ordemServicoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        when(mapper.toEntity(any(OrdemServicoDTO.class))).thenReturn(ordemServicoModel);
        when(service.update(any(OrdemServicoModel.class))).thenReturn(ordemServicoModel);
        when(mapper.toDto(any(OrdemServicoModel.class))).thenReturn(ordemServicoDTO);

        mockMvc.perform(put("/ordem-servicos/{id}", ordemServicoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ordemServicoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(ordemServicoId);
        mockMvc.perform(delete("/ordem-servicos/{id}", ordemServicoId))
                .andExpect(status().isNoContent());
    }
}

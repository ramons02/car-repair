package br.edu.senai.fatesg.ads3.car_repair.business.mecanico;

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

@WebMvcTest(MecanicoController.class)
public class MecanicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IMecanicoService service;

    @MockBean
    private MecanicoMapper mapper;

    private MecanicoModel mecanicoModel;
    private MecanicoDTO mecanicoDTO;
    private UUID mecanicoId;

    @BeforeEach
    void setUp() {
        mecanicoId = UUID.randomUUID();
        mecanicoModel = new MecanicoModel();
        mecanicoModel.setId(mecanicoId);
        mecanicoModel.setNome("João Mecânico");

        mecanicoDTO = new MecanicoDTO();
        mecanicoDTO.setId(mecanicoId);
        mecanicoDTO.setNome("João Mecânico");
    }

    @Test
    void testGetById() throws Exception {
        when(service.findByIdActive(mecanicoId)).thenReturn(mecanicoModel);
        when(mapper.toDto(mecanicoModel)).thenReturn(mecanicoDTO);

        mockMvc.perform(get("/mecanicos/{id}", mecanicoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Mecânico"));
    }

    @Test
    void testInsert() throws Exception {
        when(mapper.toEntity(any(MecanicoDTO.class))).thenReturn(mecanicoModel);
        when(service.insert(any(MecanicoModel.class))).thenReturn(mecanicoModel);
        when(mapper.toDto(any(MecanicoModel.class))).thenReturn(mecanicoDTO);

        mockMvc.perform(post("/mecanicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mecanicoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        when(mapper.toEntity(any(MecanicoDTO.class))).thenReturn(mecanicoModel);
        when(service.update(any(MecanicoModel.class))).thenReturn(mecanicoModel);
        when(mapper.toDto(any(MecanicoModel.class))).thenReturn(mecanicoDTO);

        mockMvc.perform(put("/mecanicos/{id}", mecanicoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mecanicoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(mecanicoId);
        mockMvc.perform(delete("/mecanicos/{id}", mecanicoId))
                .andExpect(status().isNoContent());
    }
}

package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

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

@WebMvcTest(VeiculoController.class)
public class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IVeiculoService service;

    @MockBean
    private VeiculoMapper mapper;

    private VeiculoModel veiculoModel;
    private VeiculoDTO veiculoDTO;
    private UUID veiculoId;

    @BeforeEach
    void setUp() {
        veiculoId = UUID.randomUUID();
        veiculoModel = new VeiculoModel();
        veiculoModel.setId(veiculoId);
        veiculoModel.setMarca("Toyota");

        veiculoDTO = new VeiculoDTO();
        veiculoDTO.setId(veiculoId);
        veiculoDTO.setMarca("Toyota");
    }

    @Test
    void testGetById() throws Exception {
        when(service.findByIdActive(veiculoId)).thenReturn(veiculoModel);
        when(mapper.toDto(veiculoModel)).thenReturn(veiculoDTO);

        mockMvc.perform(get("/veiculos/{id}", veiculoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Toyota"));
    }

    @Test
    void testInsert() throws Exception {
        when(mapper.toEntity(any(VeiculoDTO.class))).thenReturn(veiculoModel);
        when(service.insert(any(VeiculoModel.class))).thenReturn(veiculoModel);
        when(mapper.toDto(any(VeiculoModel.class))).thenReturn(veiculoDTO);

        mockMvc.perform(post("/veiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        when(mapper.toEntity(any(VeiculoDTO.class))).thenReturn(veiculoModel);
        when(service.update(any(VeiculoModel.class))).thenReturn(veiculoModel);
        when(mapper.toDto(any(VeiculoModel.class))).thenReturn(veiculoDTO);

        mockMvc.perform(put("/veiculos/{id}", veiculoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(veiculoId);
        mockMvc.perform(delete("/veiculos/{id}", veiculoId))
                .andExpect(status().isNoContent());
    }
}

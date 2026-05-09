package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

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

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IClienteService service;

    @MockBean
    private ClienteMapper mapper;

    private ClienteModel clienteModel;
    private ClienteDTO clienteDTO;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        
        clienteModel = new ClienteModel();
        clienteModel.setId(clienteId);
        clienteModel.setNome("Teste Cliente");
        clienteModel.setCpf("11122233344");

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(clienteId);
        clienteDTO.setNome("Teste Cliente");
        clienteDTO.setCpf("11122233344");
    }

    @Test
    void testGetById() throws Exception {
        when(service.findByIdActive(clienteId)).thenReturn(clienteModel);
        when(mapper.toDto(clienteModel)).thenReturn(clienteDTO);

        mockMvc.perform(get("/clientes/{id}", clienteId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste Cliente"))
                .andExpect(jsonPath("$.cpf").value("11122233344"));
    }

    @Test
    void testInsert() throws Exception {
        when(mapper.toEntity(any(ClienteDTO.class))).thenReturn(clienteModel);
        when(service.insert(any(ClienteModel.class))).thenReturn(clienteModel);
        when(mapper.toDto(any(ClienteModel.class))).thenReturn(clienteDTO);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Teste Cliente"));
    }

    @Test
    void testUpdate() throws Exception {
        when(mapper.toEntity(any(ClienteDTO.class))).thenReturn(clienteModel);
        when(service.update(any(ClienteModel.class))).thenReturn(clienteModel);
        when(mapper.toDto(any(ClienteModel.class))).thenReturn(clienteDTO);

        mockMvc.perform(put("/clientes/{id}", clienteId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste Cliente"));
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(service).delete(clienteId);

        mockMvc.perform(delete("/clientes/{id}", clienteId))
                .andExpect(status().isNoContent());
    }
}

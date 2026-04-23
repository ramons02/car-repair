package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;

@Component
public class ClienteMapper implements IGenericMapper<ClienteModel, ClienteDTO> {

       @Override
    public ClienteDTO toDto(ClienteModel entity) {
        if (entity == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();
        dto.setId(entity.getId());
        dto.setActive(entity.isAtivo());
        dto.setNome(entity.getNome());
        dto.setCpf(entity.getCpf());
        dto.setTelefone(entity.getTelefone());
        dto.setEndereco(entity.getEndereco());
        dto.setBairro(entity.getBairro());
        dto.setCidade(entity.getCidade());
        dto.setEstado(entity.getEstado());
        dto.setCEP(entity.getCEP());

        return dto;
    }

    @Override
    public ClienteModel toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        ClienteModel entity = new ClienteModel();
        entity.setId(dto.getId());
        entity.setAtivo(dto.isActive());
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setTelefone(dto.getTelefone());
        entity.setEndereco(dto.getEndereco());
        entity.setBairro(dto.getBairro());
        entity.setCidade(dto.getCidade());
        entity.setEstado(dto.getEstado());
        entity.setCEP(dto.getCEP());

        return entity;
    }
}

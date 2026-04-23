package br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos;

import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;

@Component
public class OrdemServicoMapper implements IGenericMapper<OrdemServicoModel, OrdemServicoDTO> {

    @Override
    public OrdemServicoDTO toDto(OrdemServicoModel entity) {
        if (entity == null) {
            return null;
        }

        OrdemServicoDTO dto = new OrdemServicoDTO();
        dto.setId(entity.getId());
        dto.setActive(entity.isAtivo());
        dto.setClienteId(entity.getCliente() != null ? entity.getCliente().getId() : null);
        dto.setVeiculoId(entity.getVeiculo() != null ? entity.getVeiculo().getId() : null);
        dto.setDataEntrada(entity.getDataEntrada());
        dto.setDataSaida(entity.getDataSaida());
        dto.setProblema(entity.getProblema());
        dto.setObservacoes(entity.getObservacoes());

        return dto;
    }

    @Override
    public OrdemServicoModel toEntity(OrdemServicoDTO dto) {
        if (dto == null) {
            return null;
        }

        OrdemServicoModel entity = new OrdemServicoModel();
        entity.setId(dto.getId());
        entity.setAtivo(dto.isActive());
        entity.setDataEntrada(dto.getDataEntrada());
        entity.setDataSaida(dto.getDataSaida());
        entity.setProblema(dto.getProblema());
        entity.setObservacoes(dto.getObservacoes());

        return entity;
    }
}

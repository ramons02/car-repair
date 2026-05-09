package br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.business.clientes.IClienteRepository;
import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.IVeiculoRepository;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;

@Component
public class OrdemServicoMapper implements IGenericMapper<OrdemServicoModel, OrdemServicoDTO> {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IVeiculoRepository veiculoRepository;

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

        if (dto.getClienteId() != null) {
            entity.setCliente(clienteRepository.getReferenceById(dto.getClienteId()));
        }

        if (dto.getVeiculoId() != null) {
            entity.setVeiculo(veiculoRepository.getReferenceById(dto.getVeiculoId()));
        }

        return entity;
    }
}

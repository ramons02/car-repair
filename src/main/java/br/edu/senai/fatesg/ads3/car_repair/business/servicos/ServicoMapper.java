package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;

@Component
public class ServicoMapper implements IGenericMapper<ServicoModel, ServicoDTO> {

    @Override
    public ServicoDTO toDto(ServicoModel entity) {
        if (entity == null) {
            return null;
        }

        ServicoDTO dto = new ServicoDTO();
        dto.setId(entity.getId());
        dto.setActive(entity.isAtivo());
        dto.setOrdemServicoId(entity.getOs() != null ? entity.getOs().getId() : null);
        dto.setDescricao(entity.getDescricao());
        dto.setDataHoraServico(entity.getData_hora_servico());
        dto.setDataHoraTermino(entity.getData_hora_termino());
        dto.setMecanico(entity.getMecanico());
        dto.setValor(entity.getValor());

        return dto;
    }

    @Override
    public ServicoModel toEntity(ServicoDTO dto) {
        if (dto == null) {
            return null;
        }

        ServicoModel entity = new ServicoModel();
        entity.setId(dto.getId());
        entity.setAtivo(dto.isActive());
        entity.setDescricao(dto.getDescricao());
        entity.setData_hora_servico(dto.getDataHoraServico());
        entity.setData_hora_termino(dto.getDataHoraTermino());
        entity.setMecanico(dto.getMecanico());
        entity.setValor(dto.getValor());

        return entity;
    }
}

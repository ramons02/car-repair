package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos.IOrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;

@Component
public class ServicoMapper implements IGenericMapper<ServicoModel, ServicoDTO> {

    @Autowired
    private IOrdemServicoRepository ordemServicoRepository;

    @Autowired
    private br.edu.senai.fatesg.ads3.car_repair.business.mecanico.IMecanicoRepository mecanicoRepository;

    @Override
    public ServicoDTO toDto(ServicoModel entity) {
        if (entity == null) {
            return null;
        }

        ServicoDTO dto = new ServicoDTO();
        dto.setId(entity.getId());
        dto.setActive(entity.isAtivo());

        // Mapeia o ID do objeto de entidade para o UUID do DTO
        dto.setOrdemServicoId(entity.getOs() != null ? entity.getOs().getId() : null);

        dto.setDescricao(entity.getDescricao());
        dto.setDataHoraServico(entity.getData_hora_servico());
        dto.setDataHoraTermino(entity.getData_hora_termino());
        dto.setMecanicoId(entity.getMecanico() != null ? entity.getMecanico().getId() : null);
        dto.setNomeMecanico(entity.getMecanico() != null ? entity.getMecanico().getNome() : null);
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

        if (dto.getOrdemServicoId() != null) {
            entity.setOs(ordemServicoRepository.getReferenceById(dto.getOrdemServicoId()));
        }

        entity.setDescricao(dto.getDescricao());
        entity.setData_hora_servico(dto.getDataHoraServico());
        entity.setData_hora_termino(dto.getDataHoraTermino());
        
        if (dto.getMecanicoId() != null) {
            entity.setMecanico(mecanicoRepository.getReferenceById(dto.getMecanicoId()));
        }
        
        entity.setValor(dto.getValor());

        return entity;
    }
}
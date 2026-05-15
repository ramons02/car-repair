package br.edu.senai.fatesg.ads3.car_repair.business.mecanico;

import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.core.helpers.IGenericMapper;

@Component
public class MecanicoMapper implements IGenericMapper<MecanicoModel, MecanicoDTO> {

    @Override
    public MecanicoDTO toDto(MecanicoModel entity) {
        if (entity == null) {
            return null;
        }

        MecanicoDTO dto = new MecanicoDTO();
        dto.setId(entity.getId());
        dto.setActive(entity.isAtivo());
        dto.setNome(entity.getNome());
        dto.setEspecialidade(entity.getEspecialidade());
        dto.setTelefone(entity.getTelefone());

        return dto;
    }

    @Override
    public MecanicoModel toEntity(MecanicoDTO dto) {
        if (dto == null) {
            return null;
        }

        MecanicoModel entity = new MecanicoModel();
        entity.setId(dto.getId());
        entity.setAtivo(dto.isActive());
        entity.setNome(dto.getNome());
        entity.setEspecialidade(dto.getEspecialidade());
        entity.setTelefone(dto.getTelefone());

        return entity;
    }
}

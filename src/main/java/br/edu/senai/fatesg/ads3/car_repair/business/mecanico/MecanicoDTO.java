package br.edu.senai.fatesg.ads3.car_repair.business.mecanico;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MecanicoDTO extends BaseDTO {
    private String nome;
    private String especialidade;
    private String telefone;
}

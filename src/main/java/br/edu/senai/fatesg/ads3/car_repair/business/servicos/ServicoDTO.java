package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServicoDTO extends BaseDTO {
    private java.util.UUID ordemServicoId;
    private String descricao;
    private Date dataHoraServico;
    private Date dataHoraTermino;
    private String mecanico;
    private double valor;
}

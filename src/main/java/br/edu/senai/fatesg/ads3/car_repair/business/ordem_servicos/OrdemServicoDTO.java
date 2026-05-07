package br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrdemServicoDTO extends BaseDTO {
    private java.util.UUID clienteId;
    private java.util.UUID veiculoId;
    private Date dataEntrada;
    private Date dataSaida;
    private String problema;
    private String observacoes;
}

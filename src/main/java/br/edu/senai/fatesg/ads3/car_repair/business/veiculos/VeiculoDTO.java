package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VeiculoDTO extends BaseDTO {
    private String marca;
    private String modelo;
    private String anoFabricacao;
    private String anoModelo;
    private String placa;
    private String cor;
}

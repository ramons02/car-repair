package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.dtos.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)

public class ClienteDTO extends BaseDTO{
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String CEP;
    
}


package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class ClienteModel extends BaseModel {
    @Column(name = "nome", length = 120)
    private String nome;
    @Column(name = "cpf", length = 15)
    private String cpf;
    @Column(name = "telefone", length = 15)
    private String telefone;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "bairro", length = 70)
    private String bairro;
    @Column(name = "cidade", length = 70)
    private String cidade;
    @Column(name = "estado", length = 2)
    private String estado;
    @Column(name = "cep", length = 8)
    private String CEP;
}

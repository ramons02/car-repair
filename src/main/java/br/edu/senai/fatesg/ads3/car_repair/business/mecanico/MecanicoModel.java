package br.edu.senai.fatesg.ads3.car_repair.business.mecanico;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "mecanicos")
@Data
@EqualsAndHashCode(callSuper = true)
public class MecanicoModel extends BaseModel {
    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Column(name = "especialidade", length = 100)
    private String especialidade;

    @Column(name = "telefone", length = 15)
    private String telefone;
}

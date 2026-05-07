package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "veiculos")
public class VeiculoModel extends BaseModel {

    @Column(name = "marca", length = 50,nullable = false)
    private String marca;

    @Column(name = "modelo", length = 50,nullable = false)
    private String modelo;

    @Column(name = "ano_fabricacao", length = 4,nullable = false)
    private String anoFabricacao;

    @Column(name = "ano_modelo", length = 4,nullable = false)
    private String anoModelo;

    @Column(name = "placa", length = 7, unique = true)
    private String placa;

    @Column(name = "cor", length = 20,nullable = false)
    private String cor;

}
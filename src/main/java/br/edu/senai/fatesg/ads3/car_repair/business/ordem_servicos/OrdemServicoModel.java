package br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos;

import java.util.Date;

import br.edu.senai.fatesg.ads3.car_repair.business.veiculos.VeiculoModel;
import br.edu.senai.fatesg.ads3.car_repair.business.clientes.ClienteModel;
import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "ordem_servicos")

public class OrdemServicoModel extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel cliente;
    
    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private VeiculoModel veiculo;

    @Column(name = "data_entrada",nullable = false)
    private Date dataEntrada;

    @Column(name = "data_saida",nullable = false)
    private Date dataSaida;

    @Column(name = "problema",length = 100,nullable = false)
    private String problema;

    @Column(name = "observacoes",length = 100,nullable = false)
    private String observacoes;
    
}
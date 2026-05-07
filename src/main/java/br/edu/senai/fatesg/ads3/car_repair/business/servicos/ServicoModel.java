package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import java.sql.Date;

import br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos.OrdemServicoModel;
import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "servicos")
public class ServicoModel extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServicoModel os;

    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_hora_servico", nullable = false)
    private Date data_hora_servico;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_hora_termino", nullable = false)
    private Date data_hora_termino;

    @Column(name = "mecanico", length = 50, nullable = false)
    private String mecanico;

    @Column(name = "valor", nullable = false)
    private double valor;

}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.senai.fatesg.ads3.car_repair.core.domains;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;
import lombok.Data;

/**
 *
 * @author Clayton
 */
@Data
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   
    private UUID id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_hora_criacao")
    private Date dataHoraCriacao;
    @Column(name = "ativo")
    private boolean ativo;
    @jakarta.persistence.PrePersist
    protected void onCreate() {
        if (this.dataHoraCriacao == null) {
            this.dataHoraCriacao = new Date();
        }
        this.ativo = true;
   }
}
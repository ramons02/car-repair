package br.edu.senai.fatesg.ads3.car_repair.business.servicos;


import org.springframework.stereotype.Repository;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
@Repository

public interface IServicoRepository extends IGenericRepository<ServicoModel>{
    
}
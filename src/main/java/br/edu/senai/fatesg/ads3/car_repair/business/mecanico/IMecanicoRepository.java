package br.edu.senai.fatesg.ads3.car_repair.business.mecanico;

import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMecanicoRepository extends IGenericRepository<MecanicoModel> {
}

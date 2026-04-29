package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import org.springframework.stereotype.Service;
import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;

@Service
public class VeiculoService extends GenericService<VeiculoModel, IVeiculoRepository, IVeiculoValidation> implements IVeiculoService {

}

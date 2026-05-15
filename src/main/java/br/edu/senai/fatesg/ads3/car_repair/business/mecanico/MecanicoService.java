package br.edu.senai.fatesg.ads3.car_repair.business.mecanico;

import org.springframework.stereotype.Service;
import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;

@Service
public class MecanicoService extends GenericService<MecanicoModel, IMecanicoRepository, IMecanicoValidation> implements IMecanicoService {
}

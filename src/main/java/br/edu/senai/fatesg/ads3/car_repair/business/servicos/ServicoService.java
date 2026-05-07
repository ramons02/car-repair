package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import org.springframework.stereotype.Service;
import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;

@Service
public class ServicoService extends GenericService<ServicoModel, IServicoRepository, IServicoValidation> implements IServicoService {

}

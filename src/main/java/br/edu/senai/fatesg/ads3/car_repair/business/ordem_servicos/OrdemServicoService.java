package br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos;

import org.springframework.stereotype.Service;
import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;

@Service
public class OrdemServicoService extends GenericService<OrdemServicoModel, IOrdemServicoRepository, IOrdemServicoValidation> implements IOrdemServicoService {

}

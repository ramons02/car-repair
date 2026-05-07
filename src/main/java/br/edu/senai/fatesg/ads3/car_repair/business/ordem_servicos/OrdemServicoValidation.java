package br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos;

import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;

@Component
public class OrdemServicoValidation extends GenericValidation<OrdemServicoModel, IOrdemServicoRepository> implements IOrdemServicoValidation {

}

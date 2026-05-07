package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;

@Component
public class ServicoValidation extends GenericValidation<ServicoModel, IServicoRepository> implements IServicoValidation {

}

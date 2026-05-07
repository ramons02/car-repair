package br.edu.senai.fatesg.ads3.car_repair.business.ordem_servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.controllers.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordem-servicos")
public class OrdemServicoController extends GenericController<OrdemServicoModel, OrdemServicoDTO, IOrdemServicoService, OrdemServicoMapper> {

}

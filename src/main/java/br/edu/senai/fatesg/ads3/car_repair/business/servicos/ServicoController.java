package br.edu.senai.fatesg.ads3.car_repair.business.servicos;

import br.edu.senai.fatesg.ads3.car_repair.core.controllers.GenericController;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = "http://localhost:4200")
public class ServicoController extends GenericController<ServicoModel, ServicoDTO, IServicoService, ServicoMapper> {

}

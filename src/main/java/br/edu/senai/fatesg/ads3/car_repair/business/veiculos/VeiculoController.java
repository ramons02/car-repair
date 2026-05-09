package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import br.edu.senai.fatesg.ads3.car_repair.core.controllers.GenericController;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/veiculos")
@CrossOrigin(origins = "http://localhost:4200")
public class VeiculoController extends GenericController<VeiculoModel, VeiculoDTO, IVeiculoService, VeiculoMapper> {

}

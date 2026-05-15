package br.edu.senai.fatesg.ads3.car_repair.business.mecanico;
import br.edu.senai.fatesg.ads3.car_repair.core.controllers.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mecanicos")
public class MecanicoController extends GenericController<MecanicoModel, MecanicoDTO, IMecanicoService, MecanicoMapper> {
}

package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import br.edu.senai.fatesg.ads3.car_repair.core.controllers.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController extends GenericController<ClienteModel, ClienteDTO, IClienteService, ClienteMapper> {
    
}

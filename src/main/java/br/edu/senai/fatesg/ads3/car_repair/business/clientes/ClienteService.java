package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import org.springframework.stereotype.Service;
import br.edu.senai.fatesg.ads3.car_repair.core.services.GenericService;

@Service
public class ClienteService extends GenericService<ClienteModel, IClienteRepository, IClienteValidation> implements IClienteService {

    
}
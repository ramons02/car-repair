package br.edu.senai.fatesg.ads3.car_repair.business.veiculos;

import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;

@Component
public class VeiculoValidation extends GenericValidation<VeiculoModel, IVeiculoRepository> implements IVeiculoValidation {

}

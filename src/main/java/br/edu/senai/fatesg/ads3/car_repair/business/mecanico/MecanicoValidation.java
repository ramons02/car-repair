package br.edu.senai.fatesg.ads3.car_repair.business.mecanico;

import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;

@Component
public class MecanicoValidation extends GenericValidation<MecanicoModel, IMecanicoRepository> implements IMecanicoValidation {

    @Override
    public void validateInsert(MecanicoModel entity) {
        // Validações específicas para inserção podem ser adicionadas aqui
    }

    @Override
    public void validateUpdate(MecanicoModel entity) {
        // Validações específicas para atualização podem ser adicionadas aqui
    }
}

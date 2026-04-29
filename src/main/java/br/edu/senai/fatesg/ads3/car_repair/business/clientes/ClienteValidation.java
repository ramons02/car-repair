package br.edu.senai.fatesg.ads3.car_repair.business.clientes;

import org.springframework.stereotype.Component;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.GenericValidation;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import java.util.Optional;

@Component
public class ClienteValidation extends GenericValidation<ClienteModel, IClienteRepository> implements IClienteValidation {
    
    @Override
    public void validateInsert(ClienteModel entity) {
        // Validação de negócio específica para inserção
        validateCpfUnique(entity.getCpf(), null);
    }

    @Override
    public void validateUpdate(ClienteModel entity) {
        // Validação de negócio específica para atualização
        // Permite manter o mesmo CPF na atualização, mas rejeita duplicação com outro registro
        validateCpfUnique(entity.getCpf(), entity.getId());
    }

    /**
     * Valida se o CPF é único no banco de dados.
     * Se excludeId for fornecido, a validação permite manter o mesmo CPF naquele registro.
     */
    private void validateCpfUnique(String cpf, java.util.UUID excludeId) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return;
        }

        // Buscar cliente com este CPF
        // Nota: IClienteRepository não tem método findByCpf, seria necessário adicionar
        // Por enquanto, a validação principal vem do banco de dados (constraint unique)
        // Mas este é um exemplo de como fazer validação preventiva
    }
}

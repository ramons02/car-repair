package br.edu.senai.fatesg.ads3.car_repair.core.validations;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import java.util.UUID;


public interface IGenericValidation<E extends BaseModel, R extends IGenericRepository<E>> {
    // Validações que ocorrem SEMPRE (Insert e Update)

    void validateFields(E entity);

    // Validações de campos específicas para NOVOS registros
    default void validateFieldsInsert(E entity) {
    }

    // Validações de campos específicas para ATUALIZAÇÃO
    default void validateFieldsUpdate(E entity) {
    }

    // Regras de negócio (Contextuais)
    default void validateInsert(E entity) {
    }

    default void validateUpdate(E entity) {
    }

    default void validateDelete(UUID id) {
    }
}

package br.edu.senai.fatesg.ads3.car_repair.core.validations;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;


public abstract class GenericValidation<E extends BaseModel, R extends IGenericRepository<E>> implements IGenericValidation<E, R> {

    @Autowired
    protected R repository;

    @Override
    public void validateFields(E entity) {
        if (entity == null) {
            throw new FieldValidationException("root", "A entidade enviada não pode ser nula.");
        }
    }

    @Override
    public void validateFieldsInsert(E entity) {
        validateFields(entity);
        if (entity.getId() != null) {
            throw new FieldValidationException("id", "O ID deve ser nulo para novas inserções.");
        }
    }

    @Override
    public void validateFieldsUpdate(E entity) {
        validateFields(entity);
        if (entity.getId() == null) {
            throw new FieldValidationException("id", "O ID é obrigatório para atualizações.");
        }

        // Exemplo de uso do repository para validar se o registro existe antes de atualizar
        if (!repository.existsById(entity.getId())) {
            throw new FieldValidationException("id", "O registro com o ID informado não existe.");
        }
    }

    @Override
    public void validateDelete(UUID id) {
        if (id == null) {
            throw new FieldValidationException("id", "ID de exclusão inválido.");
        }
    }

    // Ganchos para regras de negócio (sem implementação por padrão)
    @Override
    public void validateInsert(E entity) {
    }

    @Override
    public void validateUpdate(E entity) {
    }

}

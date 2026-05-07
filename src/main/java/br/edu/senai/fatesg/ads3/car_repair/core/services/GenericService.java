package br.edu.senai.fatesg.ads3.car_repair.core.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import br.edu.senai.fatesg.ads3.car_repair.core.domains.BaseModel;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.BusinessException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.FieldValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.RuleValidationException;
import br.edu.senai.fatesg.ads3.car_repair.core.exceptions.DataAccessExceptionHandler;
import br.edu.senai.fatesg.ads3.car_repair.core.repositories.IGenericRepository;
import br.edu.senai.fatesg.ads3.car_repair.core.validations.IGenericValidation;


public abstract class GenericService<E extends BaseModel, R extends IGenericRepository<E>, V extends IGenericValidation<E, R>>
        implements IGenericService<E, R, V> {

    @Autowired
    protected R repository;

    @Autowired
    protected V validation;

    @Override
    @Transactional(readOnly = true)
    public E findByIdActive(UUID id) {
        try {
            return repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new BusinessException(
                    "O registro com o ID informado não foi encontrado ou está inativo.", HttpStatus.NOT_FOUND));
        } catch (BusinessException be) {
            throw be; // Relança nossa exceção de negócio (404)
        } catch (Exception e) {
            // Converte erros inesperados do Repository para nosso padrão
            throw new BusinessException("Erro ao localizar o registro em " + getEntityName(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<E> findAllActive(Pageable pageable) {
        try {
            return repository.findAllByAtivoTrue(pageable);
        } catch (BusinessException be) {
            throw be; // Relança nossa exceção de negócio (404)
        } catch (Exception e) {
            // Converte erros inesperados do Repository para nosso padrão
            throw new BusinessException("Erro ao localizar o registro em " + getEntityName(), e);
        }
    }

    @Override
    @Transactional
    public E insert(E entity) {
        try {

            validation.validateFieldsInsert(entity);
            validation.validateInsert(entity); // Regras de Negócio para Insert
            beforeInsert(entity);

            // 2. Persistência
            E savedEntity = repository.save(entity);

            // 3. Pós-processamento
            afterInsert(savedEntity, entity);

            return savedEntity;
        } catch (BusinessException | FieldValidationException | RuleValidationException be) {
            throw be; // Mantém a exceção de validação original (400, 409, etc)
        } catch (DataAccessException dae) {
            // Delega ao handler para extrair informações específicas do erro
            DataAccessExceptionHandler.handleDataAccessException(dae, getEntityName());
            // Nunca chegará aqui, mas Java exige para compilar
            throw dae;
        } catch (Exception e) {
            throw new BusinessException("Erro inesperado ao processar inserção em " + getEntityName(), e);
        }
    }

    @Override
    @Transactional
    public E update(E entity) {
        try {

            validation.validateFieldsUpdate(entity);
            validation.validateUpdate(entity); // Regras de Negócio para Update
            beforeUpdate(entity);

            // 2. Persistência
            E savedEntity = repository.save(entity);

            // 3. Pós-processamento
            afterUpdate(savedEntity, entity);

            return savedEntity;
        } catch (BusinessException | FieldValidationException | RuleValidationException be) {
            throw be;
        } catch (DataAccessException dae) {
            // Delega ao handler para extrair informações específicas do erro
            DataAccessExceptionHandler.handleDataAccessException(dae, getEntityName());
            // Nunca chegará aqui, mas Java exige para compilar
            throw dae;
        } catch (Exception e) {
            throw new BusinessException("Erro inesperado ao processar atualização em " + getEntityName(), e);
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        try {
            // Validação de regra para exclusão (ex: verificar vínculos)
            validation.validateDelete(id);
            E entity = findByIdActive(id);
            beforeDelete(entity);
            entity.setAtivo(true);
            repository.save(entity);
            afterDelete(entity);
        } catch (BusinessException | FieldValidationException | RuleValidationException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException("Não foi possível excluir o registro em " + getEntityName(), e);
        }
    }

    // Auxiliar para mensagens dinâmicas
    protected String getEntityName() {
        return this.getClass().getSimpleName().replace("Service", "");
    }

// Métodos Hook (Ganchos) para serem sobrescritos no Business
    protected void beforeInsert(E entity) {
    }

    ;

	protected void afterInsert(E entity, E old) {
    }

    ;

	protected void beforeUpdate(E entity) {
    }

    ;

	protected void afterUpdate(E entity, E old) {
    }

    ;

	protected void beforeDelete(E entity) {
    }

    ;

	protected void afterDelete(E entity) {
    }
;

}

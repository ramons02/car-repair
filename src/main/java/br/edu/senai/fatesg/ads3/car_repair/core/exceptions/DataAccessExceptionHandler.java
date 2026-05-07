package br.edu.senai.fatesg.ads3.car_repair.core.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Utilitário para extrair informações de erro do banco de dados
 * e lançar exceções apropriadas para o frontend.
 */
public class DataAccessExceptionHandler {

    /**
     * Trata exceções de acesso a dados e lança exceções de negócio apropriadas.
     * @param dae DataAccessException capturada
     * @param entityName Nome da entidade sendo processada
     * @throws BusinessException com mensagem apropriada e HTTP status correto
     */
    public static void handleDataAccessException(DataAccessException dae, String entityName) {
        // Violação de constraint unique (chave duplicada)
        if (dae instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException dive = (DataIntegrityViolationException) dae;
            String message = dive.getMostSpecificCause().getMessage();
            
            // PostgreSQL error message para unique constraint
            if (message != null && message.contains("duplicate key value violates unique constraint")) {
                // Extrai o nome do campo da mensagem de erro
                String fieldName = extractFieldNameFromUniqueError(message);
                throw new BusinessException(
                    "Valor duplicado para o campo: " + fieldName + ". Este valor já existe no sistema.",
                    "DUPLICATE_KEY",
                    org.springframework.http.HttpStatus.CONFLICT
                );
            }
            
            // Violação de constraint de integridade referencial
            if (message != null && message.contains("foreign key constraint")) {
                throw new BusinessException(
                    "Não é possível processar esta operação. Verifique as referências a outras entidades.",
                    "FOREIGN_KEY_VIOLATION",
                    org.springframework.http.HttpStatus.BAD_REQUEST
                );
            }
        }
        
        // Se não conseguir identificar o erro específico, lançar genérico
        throw new BusinessException(
            "Erro de integridade ao processar " + entityName + ". Verifique os dados enviados.",
            "DATA_INTEGRITY_ERROR",
            org.springframework.http.HttpStatus.BAD_REQUEST,
            dae
        );
    }

    /**
     * Extrai o nome do campo da mensagem de erro de unique constraint.
     * Exemplo: "uk7wflw78ibh162cmq12ii6ffly" -> "cpf"
     */
    private static String extractFieldNameFromUniqueError(String message) {
        // Tenta extrair a chave duplicada da mensagem: Key (campo)=(valor)
        if (message.contains("Key (")) {
            int start = message.indexOf("Key (") + 5;
            int end = message.indexOf(")", start);
            if (end > start) {
                return message.substring(start, end);
            }
        }
        return "desconhecido";
    }
}

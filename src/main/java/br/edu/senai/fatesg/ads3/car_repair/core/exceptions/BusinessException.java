package br.edu.senai.fatesg.ads3.car_repair.core.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BusinessException extends BaseException {

	private static final long serialVersionUID = -224350778964358350L;

	public BusinessException(String message, HttpStatus httpStatus) {
        super(
            "Erro de Processo de Negócio", // Título fixo para este tipo de exceção
            message, 
            httpStatus, 
            Severity.ERROR, 
            "BUSINESS_PROCESS_ERROR"
        );
    }

	/**
	 * Construtor com mensagem, código de erro e HTTP status.
	 */
	public BusinessException(String message, String code, HttpStatus httpStatus) {
        super(
            "Erro de Processo de Negócio",
            message, 
            httpStatus, 
            Severity.ERROR, 
            code
        );
    }

	/**
	 * Construtor com mensagem, código de erro, HTTP status e causa original.
	 */
	public BusinessException(String message, String code, HttpStatus httpStatus, Throwable cause) {
        super(
            "Erro de Processo de Negócio",
            message, 
            httpStatus, 
            Severity.ERROR, 
            code
        );
        this.initCause(cause);
    }
	
	// Construtor padrão
	public BusinessException(String message) {
		super("Erro de Negócio", message, HttpStatus.UNPROCESSABLE_ENTITY, Severity.ERROR, "BUSINESS_ERROR");
	}

	/**
	 * Construtor de Conversão: Recebe qualquer Exception e a envelopa. Útil para
	 * capturar erros inesperados e dar um contexto de negócio.
	 */
	public BusinessException(Throwable cause) {
		super("Erro Interno de Processamento", cause.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, Severity.FATAL,
				"INTERNAL_SERVER_ERROR");
		this.initCause(cause); // Mantém a causa original para o log (StackTrace)
	}

	/**
	 * Construtor de Conversão com Mensagem Customizada. Ex: "Falha ao salvar Ala" +
	 * Exceção original do SQL.
	 */
	public BusinessException(String customMessage, Throwable cause) {
		super("Erro de Operação", customMessage, HttpStatus.INTERNAL_SERVER_ERROR, Severity.ERROR, "OPERATION_FAILED");
		this.initCause(cause);
	}
}

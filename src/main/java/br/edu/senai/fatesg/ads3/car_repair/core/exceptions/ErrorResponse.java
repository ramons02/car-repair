package br.edu.senai.fatesg.ads3.car_repair.core.exceptions;

import java.time.LocalDateTime;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String title;
    private String message;

    // Construtor privado para forçar o uso dos métodos estáticos (Factory Pattern)
    private ErrorResponse(String message) {
        this("Error", message);
    }
    
    private ErrorResponse(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public static ErrorResponse error(BusinessException ex) {
        return new ErrorResponse(ex.getTitle(), ex.getMessage().concat(" -> ").concat(ex.getMotive()));
    }
    public static ErrorResponse error(FieldValidationException ex) {
    	return new ErrorResponse(ex.getTitle(), ex.getMessage().concat(" -> ").concat(ex.getMotive()));
    }
    public static  ErrorResponse error(RuleValidationException ex) {
    	return new ErrorResponse(ex.getTitle(), ex.getMessage().concat(" -> ").concat(ex.getMotive()));
    }
    public static  ErrorResponse error(BaseException ex) {
    	return new ErrorResponse(ex.getTitle(), ex.getMessage().concat(" -> ").concat(ex.getMotive()));
    }
    public static  ErrorResponse error(String message) {
        return new ErrorResponse(message);
    }
}

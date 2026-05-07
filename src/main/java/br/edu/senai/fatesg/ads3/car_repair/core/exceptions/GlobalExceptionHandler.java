package br.edu.senai.fatesg.ads3.car_repair.core.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(BusinessException.class)
   public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        // Usamos o status HTTP que você guardou dentro da BusinessException
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ErrorResponse.error(ex));
    }
    
   @ExceptionHandler(BaseException.class)
   public ResponseEntity<ErrorResponse> handleBusinessException(BaseException ex) {
        // Usamos o status HTTP que você guardou dentro da BusinessException
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ErrorResponse.error(ex));
    }
   
   @ExceptionHandler(FieldValidationException.class)
   public ResponseEntity<ErrorResponse> handleBusinessException(FieldValidationException ex) {
       // Usamos o status HTTP que você guardou dentro da BusinessException
       return ResponseEntity
               .status(ex.getHttpStatus())
               .body(ErrorResponse.error(ex));
   }
   
   @ExceptionHandler(RuleValidationException.class)
   public ResponseEntity<ErrorResponse> handleBusinessException(RuleValidationException ex) {
       // Usamos o status HTTP que você guardou dentro da BusinessException
       return ResponseEntity
               .status(ex.getHttpStatus())
               .body(ErrorResponse.error(ex));
   }
   

    // Captura erros inesperados (ex: NullPointerException)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(ErrorResponse.error("Ocorreu um erro interno no servidor."));
    }
}

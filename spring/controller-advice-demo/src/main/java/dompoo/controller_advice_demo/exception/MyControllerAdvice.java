package dompoo.controller_advice_demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyControllerAdvice {
    
    @ExceptionHandler(MyException.class)
    public ResponseEntity<ErrorResponse> doCatchIllegalState(MyException ex) {
        ErrorResponse errorResponse = ErrorResponse.makeErrorResponseFromException(ex);
        
        return ResponseEntity
            .status(ex.getStatus())
            .body(errorResponse);
    }
}

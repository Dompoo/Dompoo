package dompoo.controller_advice_demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    
    FIVE_MUL_COUNT(HttpStatus.BAD_REQUEST, "count가 5의 배수입니다.");
    
    private final HttpStatus status;
    private final String message;
    
    ErrorType(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

package dompoo.controller_advice_demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MyException extends RuntimeException {
    
    private String errorCode;
    private String message;
    private HttpStatus status;
    
    public MyException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorCode = errorEnum.getErrorCode();
        this.message = errorEnum.getMessage();
        this.status = errorEnum.getStatus();
    }
}

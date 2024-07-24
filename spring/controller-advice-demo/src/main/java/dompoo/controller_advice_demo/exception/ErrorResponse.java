package dompoo.controller_advice_demo.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    
    private final ErrorType errorType;
    private final String message;
    
    private ErrorResponse(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }
    
    public static ErrorResponse fromErrorType(ErrorType errorType) {
        return new ErrorResponse(errorType, errorType.getMessage());
    }
}

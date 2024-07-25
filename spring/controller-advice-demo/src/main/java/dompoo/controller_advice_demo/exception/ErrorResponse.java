package dompoo.controller_advice_demo.exception;

public class ErrorResponse {
    
    public final String errorCode;
    public final String message;
    
    private ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
    
    public static ErrorResponse makeErrorResponseFromException(MyException e) {
        return new ErrorResponse(e.getErrorCode(), e.getMessage());
    }
}

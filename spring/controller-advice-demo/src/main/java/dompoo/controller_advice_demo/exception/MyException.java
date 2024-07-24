package dompoo.controller_advice_demo.exception;

public abstract class MyException extends RuntimeException {
    
    private ErrorType errorType;
    
    public MyException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
    
    public ErrorType getErrorType() {
        return errorType;
    }
}

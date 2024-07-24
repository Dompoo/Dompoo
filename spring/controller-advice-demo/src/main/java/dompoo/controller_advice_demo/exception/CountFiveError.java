package dompoo.controller_advice_demo.exception;

public class CountFiveError extends MyException {
    
    public CountFiveError(ErrorType errorType) {
        super(errorType);
    }
}

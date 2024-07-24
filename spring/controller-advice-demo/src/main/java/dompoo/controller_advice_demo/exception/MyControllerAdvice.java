package dompoo.controller_advice_demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyControllerAdvice {

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> doCatchIllegalState(IllegalStateException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
    
    @ExceptionHandler(MyException.class)
    public ResponseEntity<ErrorResponse> doCatchMy(MyException ex) {
        ErrorType errorType = ex.getErrorType();
        return ResponseEntity
            .status(errorType.getStatus())
            .body(ErrorResponse.fromErrorType(errorType));
    }
}

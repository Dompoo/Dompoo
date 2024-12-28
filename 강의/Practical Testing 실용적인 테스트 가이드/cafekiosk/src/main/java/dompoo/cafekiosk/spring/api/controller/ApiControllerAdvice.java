package dompoo.cafekiosk.spring.api.controller;

import dompoo.cafekiosk.spring.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiResponse<Object> bindException(BindException ex) {
		String exceptionMessage = ex.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
		return ApiResponse.of(HttpStatus.BAD_REQUEST, exceptionMessage, null);
	}
}

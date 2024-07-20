package dompoo.kopringdemo.api.exception.util

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

	@ExceptionHandler(CustomException::class)
	fun handleRuntimeException(exception: CustomException): ResponseEntity<ErrorResponse> =
		ResponseEntity.status(exception.code).body(ErrorResponse.from(exception))
}
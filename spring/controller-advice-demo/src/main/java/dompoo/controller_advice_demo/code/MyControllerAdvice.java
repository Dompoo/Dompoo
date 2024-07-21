//package dompoo.controller_advice_demo.code;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class MyControllerAdvice {
//
//	@ExceptionHandler(IllegalStateException.class)
//	public ResponseEntity<String> doCatch(IllegalStateException ex) {
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//	}
//}

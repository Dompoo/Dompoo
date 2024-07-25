package dompoo.controller_advice_demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorEnum {
    
    COUNT_IS_FIVE_MUL(HttpStatus.BAD_REQUEST, "COUNT_IS_FIVE_MUL", "count가 5의 배수입니다!!"),
    NOT_FOUND_MEMBER_ID(HttpStatus.NOT_FOUND, "NOT_FOUND_MEMBER_ID", "존재하지 않는 회원입니다.");
    
    private final HttpStatus status;
    private final String errorCode;
    private final String message;
    
    ErrorEnum(HttpStatus status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}

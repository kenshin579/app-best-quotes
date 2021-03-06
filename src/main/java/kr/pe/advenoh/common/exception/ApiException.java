package kr.pe.advenoh.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends RuntimeException {
    private HttpStatus httpStatus;
    private String code;
    private String message;


    public ApiException(String message, HttpStatus httpStatus, String code) {
        super(message);
        this.setHttpStatus(httpStatus);
        this.setCode(code);
    }

    public ApiException(String message) {
        super(message);
        this.message = message;
    }

    public ApiException(String code, String message) {
        this(message);
        this.code = code;
    }

    public ApiException(ErrorCode exceptionCode, String... arg) {
        this(exceptionCode.getCode(), exceptionCode.getMessage(arg));
    }

    public ApiException(HttpStatus httpStatus, ErrorCode exceptionCode, String... arg) {
        this(exceptionCode.getCode(), exceptionCode.getMessage(arg));
        this.httpStatus = httpStatus;
    }
}
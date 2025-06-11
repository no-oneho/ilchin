package org.groupware.ilchin.aop;

import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.groupware.ilchin.utils.Api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleException(Exception e) {
        Response<Void> errorResponse = Api.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response<Void>> handleCustomException(CustomException e) {
        Response<Void> errorResponse = Api.error(e.getError().getStatus(), e.getMessage());
        return new ResponseEntity<>(errorResponse, e.getError().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        Response<Map<String, String>> errorResponse = Api.error(HttpStatus.BAD_REQUEST, "유효성 검증에 실패하였습니다.", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        
    }
}

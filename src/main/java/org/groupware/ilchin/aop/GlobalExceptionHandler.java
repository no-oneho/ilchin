package org.groupware.ilchin.aop;

import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import utils.Api;

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
}

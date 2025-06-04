package org.groupware.ilchin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import utils.Error;

@Getter
@AllArgsConstructor
public enum DepartmentException implements Error {


    NOT_FOUND_DEPARTMENT(HttpStatus.NOT_FOUND, "부서를 찾을 수 없습니다."),
    ;


    private final HttpStatus status;
    private final String message;
}

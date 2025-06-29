package org.groupware.ilchin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.groupware.ilchin.utils.Error;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProjectException implements Error {

    NOT_FOUND_PROJECT(HttpStatus.NOT_FOUND, "프로젝트를 찾을 수 없습니다")

    ,;



    private final HttpStatus status;
    private final String message;

    }

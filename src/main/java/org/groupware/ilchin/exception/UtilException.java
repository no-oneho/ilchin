package org.groupware.ilchin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.groupware.ilchin.utils.Error;

@Getter
@AllArgsConstructor
public enum UtilException implements Error {

    FIELD_MISSING(HttpStatus.INTERNAL_SERVER_ERROR, "메서드 매개변수 오류"),;

    private final HttpStatus status;
    private final String message;

}

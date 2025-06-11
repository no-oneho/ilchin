package org.groupware.ilchin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.groupware.ilchin.utils.Error;

@Getter
@AllArgsConstructor
public enum SampleError implements Error {


    SAMPLE_ERROR(HttpStatus.BAD_REQUEST, "sample error")
    ;

    private final HttpStatus status;
    private final String message;

}

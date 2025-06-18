package org.groupware.ilchin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.groupware.ilchin.utils.Error;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExtendApiException implements Error {

    BAD_REQUEST_INFO(HttpStatus.BAD_REQUEST, "입력한 정보를 다시 확인해주세요")

    ,;



    private final HttpStatus status;
    private final String message;

}

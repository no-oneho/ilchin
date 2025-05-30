package org.groupware.ilchin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import utils.Error;

@Getter
@AllArgsConstructor
public enum UserException implements Error {

    ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 사용중인 사용자명 입니다."),
    MISS_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    BAD_REQUEST_LOGIN(HttpStatus.BAD_REQUEST, "로그인 정보가 잘못되었습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}

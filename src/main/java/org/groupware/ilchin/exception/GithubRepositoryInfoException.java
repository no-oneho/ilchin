package org.groupware.ilchin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.groupware.ilchin.utils.Error;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GithubRepositoryInfoException implements Error {

    NOT_FOUND_REPOSITORY(HttpStatus.NOT_FOUND, "깃허브 레파지토리를 찾을 수 없습니다.")

    ,;



    private final HttpStatus status;
    private final String message;

}

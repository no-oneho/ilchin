package org.groupware.ilchin.utils;

import org.springframework.http.HttpStatus;

public interface Error {
    HttpStatus getStatus();
    String getMessage();
}

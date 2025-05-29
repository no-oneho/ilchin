package org.groupware.ilchin.exception;

import lombok.Getter;
import utils.Error;

@Getter
public class CustomException extends RuntimeException{

    private final Error error;
    private final String errorMessage;

    public CustomException(Error error) {
        super(error.getMessage());
        this.error = error;
        this.errorMessage = error.getMessage();
    }

}

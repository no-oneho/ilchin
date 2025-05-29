package org.groupware.ilchin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@Builder
public class Response<T> {

    private String message;
    private Integer code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @Builder
    public Response(String message, Integer code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

}

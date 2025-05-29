package utils;

import org.groupware.ilchin.dto.Response;
import org.springframework.http.HttpStatus;

public class Api {
    public static <T> Response<T> success(HttpStatus code, String message, T data) {
        return new Response<>(message, code.value(), data);
    }

    public static <T> Response<T> success(Integer code, String message, T data) {
        return new Response<>(message, code, data);
    }

    public static <T> Response<T> error(Integer code, String message) {
        return new Response<>(message, code, null);
    }

    public static <T> Response<T> error(HttpStatus code, String message) {
        return new Response<>(message, code.value(), null);
    }

}
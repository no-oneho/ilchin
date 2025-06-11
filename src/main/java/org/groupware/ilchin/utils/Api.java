package org.groupware.ilchin.utils;

import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.exception.CustomException;
import org.groupware.ilchin.exception.UtilException;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;

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

    public static <T> Response<T> error(Integer code, String message, T data) {
        return new Response<>(message, code, data);
    }

    public static <T> Response<T> error(HttpStatus code, String message, T data) {
        return new Response<>(message, code.value(), data);
    }

    public static boolean areFieldsNotNullOrEmpty(Object obj, String... fields) {
        try {
            for (String field : fields) {
                Field field1 = obj.getClass().getDeclaredField(field);
                field1.setAccessible(true);
                Object value = field1.get(obj);
                if (value == null) {
                    return false;
                }
                if (value instanceof String && ((String) value).trim().isEmpty()) {
                    return false;
                }

            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new CustomException(UtilException.FIELD_MISSING);
        }
        return true;
    }

}
package org.groupware.ilchin.controller;


import org.groupware.ilchin.dto.Response;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.groupware.ilchin.utils.Api;

import java.util.List;

class ResponseControllerTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void 정상_반환_테스트_숫자() throws Exception {
        String message = "성공";
        Integer data = 1;
        Integer code = 200;

        Response<Integer> response = Api.success(code, message, data);
        assertNotNull(response);
        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    void 정상_반환_테스트_문자열() throws Exception {
        String message = "성공";
        String data = "데이터";
        Integer code = 200;

        Response<String> response = Api.success(code, message, data);
        assertNotNull(response);
        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    void 정상_반환_테스트_리스트() throws Exception {
        String message = "성공";
        List<Integer> data = List.of(1, 2, 3);
        Integer code = 200;

        Response<List<Integer>> response = Api.success(code, message, data);
        assertNotNull(response);
        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
    }

    @Test
    void 정상_반환_테스트_HTTP_코드() throws Exception {
        String message = "성공";
        Integer data = 1;

        Response<Integer> response = Api.success(HttpStatus.OK, message, data);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(message, response.getMessage());
        assertEquals(data, response.getData());
    }



    @Test
    void 에러_반환_테스트() throws Exception {
        Integer code = 400;
        String message = "잘못된 요청";

        Response<Void> response = Api.error(code, message);

        assertNotNull(response);
        assertEquals(message, response.getMessage());
        assertEquals(code, response.getCode());
        assertNull(response.getData());
    }

    @Test
    public void 에러_반환_테스트_HTTP_코드() {
        String message = "서버 오류";
        Response<Void> response = Api.error(HttpStatus.INTERNAL_SERVER_ERROR, message);

        assertNotNull(response);
        assertEquals(message, response.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getCode()); // 검증
        assertNull(response.getData());
    }

}

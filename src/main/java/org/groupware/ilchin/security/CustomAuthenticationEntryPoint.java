package org.groupware.ilchin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.groupware.ilchin.dto.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException,
            ServletException {
        // 커스텀 에러 DTO를 설정합니다.
        Response<String> exceptionResponse = new Response<>(401, "로그인이 필요합니다.");

        // 응답 상태 코드를 401로 설정합니다 (인증 실패).
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 응답의 콘텐츠 타입을 JSON으로 설정합니다.
        response.setContentType("application/json");

        response.setCharacterEncoding("utf-8");

        // JSON 형태로 에러 DTO를 응답 본문에 씁니다.
        try (PrintWriter writer = response.getWriter()) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(exceptionResponse));
        }


    }
}

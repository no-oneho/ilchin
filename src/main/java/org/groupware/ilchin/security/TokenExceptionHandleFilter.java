package org.groupware.ilchin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.exception.UserException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.groupware.ilchin.utils.Error;

import java.io.IOException;
import java.io.PrintWriter;

public class TokenExceptionHandleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws
            ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SignatureException | ExpiredJwtException e) {
            setErrorResponse(response, UserException.CANT_ACCESS);
        } catch (AccessDeniedException e) {
            setErrorResponse(response, UserException.HANDLE_ACCESS_DENIED);
        } catch (JwtException e) {
            filterChain.doFilter(request, response);
        }
    }

    private void setErrorResponse(HttpServletResponse response, Error errorCode) throws IOException {
        Response<String> exceptionResponse = new Response<>(errorCode.getStatus().value(), errorCode.getMessage());

        // 응답 상태 코드를 401로 설정 (인증 실패).
        response.setStatus(errorCode.getStatus().value());

        response.setContentType("application/json");

        response.setCharacterEncoding("utf-8");

        try (PrintWriter writer = response.getWriter()) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(exceptionResponse));
        }
    }

}
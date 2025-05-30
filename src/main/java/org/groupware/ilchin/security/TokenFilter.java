package org.groupware.ilchin.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //헤더에 토큰을 담는 속성이 없으면 비로그인으로 판단
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        //Bearer 로 시작하는 정상 토큰인지 유효성 검사
        if (!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.split(" ")[1];

        //토큰 만료시간 검사
        if (TokenProvider.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = TokenProvider.getUserIdFromToken(token);
        String username = TokenProvider.getUsernameFromToken(token);
        String role = TokenProvider.getRoleFromToken(token);

        CustomUserDetails userDetails = new CustomUserDetails(role);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);

    }
}

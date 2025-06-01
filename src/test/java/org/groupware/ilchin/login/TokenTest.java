package org.groupware.ilchin.login;

import io.jsonwebtoken.Claims;
import org.groupware.ilchin.entity.User;
import org.groupware.ilchin.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TokenTest {

    private TokenProvider tokenProvider;
    private String token;
    private final User user = User.builder()
            .id(1L)
            .username("test")
            .password("<PASSWORD>")
            .build();

    @BeforeEach
    public void setUp() {
        tokenProvider = new TokenProvider();
        tokenProvider.setSecretKey("test");
        token = tokenProvider.createToken(user);
    }

//    @Test
//    public void 토큰정보_확인_테스트() {
//        Claims claims = tokenProvider.extractClaims(token);
//        assertEquals(Long.valueOf(claims.get("id").toString()), user.getId());
//        assertEquals(claims.get("username").toString(), user.getUsername());
//    }



}

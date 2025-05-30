package org.groupware.ilchin.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.groupware.ilchin.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    private static String SECRET_KEY;


    @Value("${secret-key-source}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    public static String createToken(User user) {
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setClaims(claims)
                .setExpiration(expiryDate)
                .compact();
    }

    public static Long getUserIdFromToken(String token) {
        return extractClaims(token).get("id", Long.class);
    }

    public static String getUsernameFromToken(String token) {
        return extractClaims(token).get("username", String.class);
    }

    public static String getRoleFromToken(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public static boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }

    private static Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

}

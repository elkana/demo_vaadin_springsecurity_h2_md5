package com.example.demovaadin.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenService {
    // public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours !
    // public static final long JWT_TOKEN_VALIDITY = 1 * 60; // 1 minutes

    @Value("${jwt.encryption.secret}")
    private String secret;
    @Value("${jwt.token.expirationSeconds}")
    private long jwtExpirationSeconds;
    // https://www.javainuse.com/webseries/spring-security-jwt/chap7
    // https://github.com/bezkoder/spring-boot-refresh-token-jwt
    @Value("${jwt.tokenRefresh.expirationSeconds}")
    private long jwtRefreshExpirationSeconds;

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(final String userId) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationSeconds * 1000))
                // .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        // .sign(this.hmac512);
    }

    public String generateRefreshToken(final String userId) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + jwtRefreshExpirationSeconds * 1000))
                // .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        // .sign(this.hmac512);
    }

    public String validateTokenAndGetUsername(final String token) throws ExpiredJwtException {
        // check expiry
        try {
            getExpirationDateFromToken(token).before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("Token expired {} seconds. {}", jwtExpirationSeconds, e.getMessage());
            return null;
        }

        try {
            return getUsernameFromToken(token);
        } catch (Exception ex) {
            log.warn("Token invalid: {}", ex.getMessage());
            return null;
        }
    }
}

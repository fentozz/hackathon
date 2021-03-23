package org.bars.hackathon.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Провайдер для аутентификации/авторизации в сигма сегменте
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Сформировать токен
     *
     * @param username логин пользователя
     * @param role     роль пользователя
     * @return токен
     */
    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Получить экземпляр авторизации по токену
     *
     * @param token токену
     * @return экземпляр авторизации
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        if (userDetails == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Получить токен из хедера
     *
     * @param req запрос
     * @return токен
     */
    String resolveToken(HttpServletRequest req) {
        return req.getHeader("token");
    }

    /**
     * Проверить токен на корректность
     *
     * @param token токен
     * @return результат true - токен прошел проверку, false - токен не прошел проверку
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
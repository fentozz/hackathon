package org.bars.hackathon.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.bars.hackathon.consts.Message;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Входной url авторизации в сигма сегменте
 */
@Slf4j
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        String message = Message.AUTH_ERROR.getMessage();
        try {
            log.error("Error auth: {}", message);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
        } catch (IOException e) {
            log.error(message);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
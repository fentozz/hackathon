package org.bars.hackathon.handlers;

import lombok.RequiredArgsConstructor;
import org.bars.hackathon.helpers.HttpRequestHelper;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dao.repository.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Instant;

/**
 * Глобальный обработчик запросов
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalResponseSuccessHandler implements ResponseBodyAdvice {

    private final HttpRequestHelper httpRequestHelper;

    private final UserRepository userRepository;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAuthenticated(authentication)) {
            updateLastAccessDate(request);
        }
        return body;
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated()
                && !authentication.getAuthorities().isEmpty();
    }

    private void updateLastAccessDate(ServerHttpRequest request) {
        UserEntity user = httpRequestHelper.getUser(request);
        user.setLastAccessed(Instant.now());
        userRepository.save(user);
    }
}
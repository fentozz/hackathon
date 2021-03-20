package org.bars.hackathon.helpers;

import lombok.RequiredArgsConstructor;
import org.bars.hackathon.consts.Message;
import org.bars.hackathon.handlers.exception.BusinessRuntimeException;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dao.repository.UserRepository;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Класс хелпер для работы с http запросами
 */
@Component
@RequiredArgsConstructor
public class HttpRequestHelper {

    private final UserRepository userRepository;

    /**
     * Получить id пользователя из текущей сессии
     *
     * @param httpRequest запрос от клиента
     * @return id пользователя
     */
    public Long getUserId(HttpServletRequest httpRequest) {
        return getUser(httpRequest).getId();
    }

    /**
     * Получить пользователя из текущей сессии
     *
     * @param httpRequest запрос от клиента
     * @return авторизованный пользователь
     */
    public UserEntity getUser(HttpServletRequest httpRequest) {
        String login = httpRequest.getUserPrincipal().getName();
        return userRepository.findOneByLogin(login)
                .orElseThrow(() -> new BusinessRuntimeException(Message.AUTH_ERROR.getMessage()));
    }

    /**
     * Получить пользователя из текущей сессии
     *
     * @param httpRequest запрос от клиента
     * @return авторизованный пользователь
     */
    public UserEntity getUser(ServerHttpRequest httpRequest) {
        Principal principal = httpRequest.getPrincipal();
        if (principal != null && principal.getName() != null) {
            return userRepository.findOneByLogin(principal.getName())
                    .orElseThrow(() -> new BusinessRuntimeException(Message.AUTH_ERROR.getMessage()));
        }
        throw new BusinessRuntimeException(Message.AUTH_ERROR.getMessage());
    }

    /**
     * Разъединить сессию, использовать только в сигма сегмента
     *
     * @param httpRequest запрос от клиента
     */
    public void terminateSession(HttpServletRequest httpRequest) {
        httpRequest.getSession().invalidate();
    }
}
package org.bars.hackathon.service.accounts.dao.entity.mapper;

import lombok.RequiredArgsConstructor;
import org.bars.hackathon.security.jwt.JwtTokenProvider;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Компонент отвечает за маппинг {@link UserEntity}
 */
@Component
@RequiredArgsConstructor
public class UserEntityMapper {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Создать пользователя
     *
     * @param login    иденификатор учетной записи
     * @param role     роль пользователя
     * @param password пароль
     * @return пользователь
     */
    public UserEntity map(@NotNull String login, @NotNull UserRoleEnum role, String password) {
        String passwordEncode = password != null ?
                jwtTokenProvider.passwordEncoder().encode(password) : null;
        return UserEntity.builder()
                .login(login)
                .userRole(role)
                .password(passwordEncode)
                .build();
    }
}
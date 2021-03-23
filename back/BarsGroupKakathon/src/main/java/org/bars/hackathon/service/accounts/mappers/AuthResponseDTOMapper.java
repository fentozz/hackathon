package org.bars.hackathon.service.accounts.mappers;

import org.bars.hackathon.consts.Message;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dto.auth.AuthResponseDTO;

/**
 * Маппер для {@link AuthResponseDTO}
 */
public class AuthResponseDTOMapper {

    /**
     * Маппинг
     *
     * @param token токен для авторизации
     * @param user  авторизованный пользователь
     * @return данные для авторизации
     */
    public AuthResponseDTO map(String token, UserEntity user) {
        return AuthResponseDTO.builder()
                .token(token)
                .message(Message.LOGIN_SUCCESS.getMessage())
                .role(user.getUserRole())
                .login(user.getLogin())
                .build();
    }
}
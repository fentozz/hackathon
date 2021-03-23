package org.bars.hackathon.service.accounts.dto.info.mapper;

import lombok.RequiredArgsConstructor;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dto.info.AccountInfoResponseDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountInfoResponseDTOMapper {

    /**
     * Маппинг информации о пользователе
     *
     * @param user авторизованный пользователь
     * @return информация о пользователе
     */
    public AccountInfoResponseDTO map(UserEntity user) {
        return AccountInfoResponseDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .created(user.getCreated())
                .lastAccessed(user.getLastAccessed())
                .build();
    }
}
package org.bars.hackathon.service.accounts.dto.info.mapper;

import lombok.RequiredArgsConstructor;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dto.info.AccountSettingsResponseDTO;
import org.springframework.stereotype.Component;

/**
 * Маппер настроек пользователя
 */
@Component
@RequiredArgsConstructor
public class AccountSettingsResponseDTOMapper {

    public AccountSettingsResponseDTO map(UserEntity user) {
        return AccountSettingsResponseDTO.builder()
                .role(user.getUserRole())
                .build();
    }
}
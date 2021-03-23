package org.bars.hackathon.service.admin.dto.mappers;

import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.admin.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

/**
 * Маппер для {@link UserResponseDTO}
 */
@Component
public class UserResponseMapper {

    public UserResponseDTO map(UserEntity user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getLogin())
                .name(user.getName())
                .role(user.getUserRole().name())
                .surname(user.getSurname())
                .created(user.getCreated())
                .changed(user.getChanged())
                .lastAccessed(user.getLastAccessed())
                .build();
    }
}

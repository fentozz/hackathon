package org.bars.hackathon.service.accounts.dao.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Буквенный идентификатор статусов пользователя
 */
@Getter
@AllArgsConstructor
public enum UserRoleEnum {

    /**
     * Администратор
     */
    SPM_ADMIN("Администратор"),
    /**
     * Пользователь системы
     */
    SPM_USER("Пользователь");

    private final String description;
}
package org.bars.hackathon.service.accounts.dto.registration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Форма регистрации нового клиента
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RegistrationRequest", description = "Форма регистрации")
public class RegistrationRequestDTO implements Serializable {

    private static final long serialVersionUID = 2768500073298123647L;

    /**
     * Логин
     */
    @Schema(description = "Логин пользователя", example = "ASeGrishin@sberbank.ru", required = true)
    private String login;

    /**
     * Пароль
     */
    @Schema(description = "Пароль пользователя", example = "ASeGrishin@sberbank.ru", required = true)
    private String password;

    /**
     * Повторное значение пароля
     */
    @Schema(description = "Повторное значение пароля пользователя", example = "ASeGrishin@sberbank.ru", required = true)
    private String passwordConfirm;
}
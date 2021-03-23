package org.bars.hackathon.service.accounts.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Запрос на аутентификацию пользователя
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AuthRequest", description = "Форма аутентификации для пользователя")
public class AuthRequestDTO implements Serializable {

    private static final long serialVersionUID = -1000196115039048449L;

    /**
     * Логин
     */
    @NotNull
    @Schema(description = "Логин пользователя", example = "ASeGrishin@sberbank.ru", required = true)
    private String login;

    /**
     * Пароль
     */
    @NotNull
    @Schema(description = "Пароль пользователя", example = "ASeGrishin@sberbank.ru", required = true)
    private String password;
}
package org.bars.hackathon.service.accounts.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Ответ об успешной авторизации
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AuthResponse", description = "Ответ об успешной авторизации")
public class AuthResponseDTO implements Serializable {

    private static final long serialVersionUID = 12792420764563571L;

    /**
     * Логин пользователя
     */
    @NotNull
    @Schema(description = "Логин пользователя", example = "test@sberbank.ru", required = true)
    private String login;

    /**
     * Роль входа
     */
    @NotNull
    @Schema(description = "Роль авторизованного пользователя", example = "SPM_USER", required = true)
    private UserRoleEnum role;

    /**
     * Токен авторизации
     */
    @NotNull
    @Schema(description = "Токен аутентификации", example = "ergergerg67834tkj", required = true)
    private String token;

    /**
     * Сообщение при авторизации
     */
    @Schema(description = "Сообщение об успешной авторизации", example = "Успешно", required = true)
    private String message;
}
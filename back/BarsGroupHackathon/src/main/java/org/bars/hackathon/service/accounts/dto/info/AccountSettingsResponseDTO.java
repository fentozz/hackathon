package org.bars.hackathon.service.accounts.dto.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;

/**
 * Настройки аккаунта
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AccountSettingsResponse", description = "Настройки учетной записи пользователя")
public class AccountSettingsResponseDTO {

    /**
     * Роль пользователя
     */
    @Schema(description = "Роль пользователя", example = "SPM_USER", required = true)
    private UserRoleEnum role;
}

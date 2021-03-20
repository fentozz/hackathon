package org.bars.hackathon.service.accounts.dto.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Форма для передачи информации о пользователе
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "AccountInfoResponse", description = "Форма для передачи информации о пользователе")
public class AccountInfoResponseDTO {

    /**
     * Имя пользователя
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Имя пользователя", example = "Вадим")
    private String name;

    /**
     * Фамилия пользователя
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Фамилия пользователя", example = "Царьков")
    private String surname;

    /**
     * Логин
     */
    @NotNull
    @Schema(description = "Логин", example = "tsarkov@yandex.ru", required = true)
    private String login;

    /**
     * Дата создания аккаунта
     */
    @NotNull
    @Schema(description = "Дата создания", example = "2020-06-20T15:00:07.210Z", required = true)
    private Instant created;

    /**
     * Дата последней активности пользователя
     */
    @NotNull
    @Schema(description = "Дата обновления", example = "2020-06-20T15:00:07.210Z", required = true)
    private Instant lastAccessed;
}
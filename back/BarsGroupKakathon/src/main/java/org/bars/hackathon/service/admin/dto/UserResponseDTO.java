package org.bars.hackathon.service.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Информация о пользователе
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserResponse", description = "Информация о пользователе")
public class UserResponseDTO {

    @NotNull
    @Schema(description = "ID пользователя", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Почта пользователя", example = "fdibbert@sberbank.ru")
    private String email;

    @NotNull
    @Schema(description = "Роль пользователя", example = "SPM_USER")
    private String role;

    @NotNull
    @Schema(description = "Имя пользователя", example = "Андрей")
    private String name;

    @NotNull
    @Schema(description = "Фамилия пользователя", example = "Моисеев")
    private String surname;

    @NotNull
    @Schema(description = "Дата создания", example = "1")
    private Instant created;

    @NotNull
    @Schema(description = "Дата изменения", example = "1")
    private Instant changed;

    @NotNull
    @Schema(description = "Дата последнего входа", example = "1")
    private Instant lastAccessed;
}
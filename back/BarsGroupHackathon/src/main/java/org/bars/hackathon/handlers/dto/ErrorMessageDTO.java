package org.bars.hackathon.handlers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Объект для передачи ошибки
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ErrorMessage", description = "Ошибка выполнения запроса")
public class ErrorMessageDTO implements Serializable {

    private static final long serialVersionUID = 6444630464531821163L;

    /**
     * Сообщение
     */
    @Schema(description = "Сообщение об успешном выполнении", example = "Неправильный логин или пароль", required = true)
    private String message;
}
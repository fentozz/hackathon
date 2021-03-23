package org.bars.hackathon.handlers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Объект для передачи сообщения при успешном выполнении
 */
@Data
@AllArgsConstructor
@Schema(name = "SuccessMessage", description = "Успешное выполнение запроса")
public class SuccessMessageDTO implements Serializable {

    private static final long serialVersionUID = -7414356697465253378L;

    /**
     * Сообщение
     */
    @Schema(description = "Сообщение об успешном выполнении", example = "Успешная аутентификация", required = true)
    private String message;
}
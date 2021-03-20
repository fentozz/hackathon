package org.bars.hackathon.handlers.dto.mapper;

import org.bars.hackathon.handlers.dto.ErrorMessageDTO;
import org.springframework.stereotype.Component;

/**
 * Компонент отвечает за маппинг {@link ErrorMessageDTO}
 */
@Component
public class ErrorMessageMapper {
    public ErrorMessageDTO map(String message){
        return ErrorMessageDTO.builder()
                .message(message)
                .build();
    }
}
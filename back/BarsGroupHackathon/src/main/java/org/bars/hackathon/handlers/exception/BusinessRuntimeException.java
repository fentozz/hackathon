package org.bars.hackathon.handlers.exception;

import lombok.Getter;

/**
 * Исключение возникает при ошибке бизнес логики
 */
@Getter
public class BusinessRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1747916157274678273L;

    private Long objectId;

    private String param;

    /**
     * Ошибка бизнес логики со свободным текстом ошибки
     *
     * @param type  тип ошибки
     * @param error текст ошибки
     */
    public BusinessRuntimeException(String error) {
        super(error);
        this.param = error;
    }
}
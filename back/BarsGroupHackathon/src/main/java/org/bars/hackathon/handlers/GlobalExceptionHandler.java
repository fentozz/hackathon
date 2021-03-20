package org.bars.hackathon.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bars.hackathon.consts.Message;
import org.bars.hackathon.handlers.dto.ErrorMessageDTO;
import org.bars.hackathon.handlers.dto.mapper.ErrorMessageMapper;
import org.bars.hackathon.handlers.exception.BusinessRuntimeException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Глобальный перехватчик исключений, требуется для выдачи клиенту определенного статуса при возникновении ошибки
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorMessageMapper mapper;

    private final ObjectMapper objectMapper;
    /**
     * Отловить ошибку бизнес логики
     *
     * @param ex исключение бизнес логики
     * @return сообщение об ошибке
     */
    @ExceptionHandler(BusinessRuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO handleBusinessException(BusinessRuntimeException ex) {
        return mapper.map(ex.getMessage());
    }

    /**
     * Отловить ошибку аутентификации
     *
     * @param ex исключение аутентификации
     * @return сообщение об ошибке
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessageDTO handleUsernameNotFoundException(AuthenticationException ex) {
        return mapper.map(Message.LOGIN_OR_PASSWORD_INVALID.getMessage());
    }

    /**
     * Отловить ошибку авторизации
     *
     * @param ex исключение авторизации
     * @return сообщение об ошибке
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessageDTO handlePermissionDeniedException(AccessDeniedException ex) {
        return mapper.map(null);
    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBindExceptions(BindException ex) {
        return ex.getFieldErrors().stream()
                .filter(x -> x.getCode() != null)
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getCode));
    }

    /**
     * Отловить ошибку десериализации
     *
     * @param ex исключение
     * @return сообщение об ошибке
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO handleValidationError(HttpMessageNotReadableException ex) {
        return mapper.map(ex.getMessage());
    }

    /**
     * Отловить непредвиденную ошибку
     *
     * @param ex исключение
     * @return сообщение об ошибке
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDTO handleError(Throwable ex) {
        log.error("Ошибка", ex);
        return mapper.map(ex.getMessage());
    }
}
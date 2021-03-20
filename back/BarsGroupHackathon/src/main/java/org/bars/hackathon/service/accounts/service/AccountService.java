package org.bars.hackathon.service.accounts.service;

import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dto.auth.AuthResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.AccountInfoResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.AccountSettingsResponseDTO;
import org.bars.hackathon.service.accounts.dto.registration.RegistrationRequestDTO;
import org.springframework.validation.BindException;

/**
 * Сервис для работы с пользователями
 */
public interface AccountService {

    /**
     * Авторизовать пользователя по логину и паролю
     *
     * @param login    логин
     * @param password пароль
     * @return результат авторизации
     */
    AuthResponseDTO authUser(String login, String password);

    /**
     * Зарегистрировать пользователя
     *
     * @param requestDTO учетные данные для регистрации
     * @throws BindException возникает при ошибке валидации входных параметров
     */
    void registerUser(RegistrationRequestDTO requestDTO) throws BindException;

    /**
     * Получить информацию о пользователе
     *
     * @param user авторизованный пользователь
     * @return вся информация о пользователе
     */
    AccountInfoResponseDTO getAccountInfo(UserEntity user);

    /**
     * Получить настройки
     *
     * @param user авторизованный пользователь
     * @return настройки
     */
    AccountSettingsResponseDTO getSettings(UserEntity user);
}
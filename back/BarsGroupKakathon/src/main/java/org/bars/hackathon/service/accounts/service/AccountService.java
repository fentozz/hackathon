package org.bars.hackathon.service.accounts.service;

import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dto.auth.AuthResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.AccountInfoResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.AccountSettingsResponseDTO;
import org.bars.hackathon.service.accounts.dto.upload.FileDTO;
import org.bars.hackathon.service.accounts.dto.upload.UploadFileDTO;

import java.util.List;

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
     * Получить информацию о пользователе
     *
     * @param user авторизованный пользователь
     * @return вся информация о пользователе
     */
    AccountInfoResponseDTO getAccountInfo(UserEntity user);

    /**
     * Сохранение файла
     * @param userEntity авторизованный пользователь
     * @param uploadFileDTO файл и метаинформация о нём
     */
    void uploadFile(UserEntity userEntity, UploadFileDTO uploadFileDTO);

    /**
     * Получить все файлы пользователя
     */
    List<FileDTO> getUserFiles(UserEntity userEntity);

    /**
     * Получить настройки
     *
     * @param user авторизованный пользователь
     * @return настройки
     */
    AccountSettingsResponseDTO getSettings(UserEntity user);
}
package org.bars.hackathon.service.admin.service;


import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dto.registration.RegistrationRequestDTO;
import org.bars.hackathon.service.admin.dto.UserCreateDTO;
import org.bars.hackathon.service.admin.dto.UserResponseDTO;
import org.springframework.validation.BindException;

import java.util.List;

public interface AdminService {

    /**
     * Получить пользоваталей АС
     *
     * @param userEntity авторизованный администратор
     * @return пользователи АС
     */
    List<UserResponseDTO> getUsers(UserEntity userEntity);

    void createUser(RegistrationRequestDTO registrationRequest) throws BindException;
}

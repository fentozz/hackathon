package org.bars.hackathon.service.admin.service;


import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.admin.dto.UserCreateDTO;
import org.bars.hackathon.service.admin.dto.UserResponseDTO;

import java.util.List;

public interface AdminService {

    /**
     * Получить пользоваталей АС
     *
     * @param userEntity авторизованный администратор
     * @return пользователи АС
     */
    List<UserResponseDTO> getUsers(UserEntity userEntity);

    boolean createUser(UserEntity userEntity, UserCreateDTO userCreate);
}

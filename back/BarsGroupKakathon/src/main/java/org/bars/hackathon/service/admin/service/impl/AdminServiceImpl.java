package org.bars.hackathon.service.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bars.hackathon.service.accounts.dao.entity.SettingsEntity;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;
import org.bars.hackathon.service.accounts.dao.entity.mapper.UserEntityMapper;
import org.bars.hackathon.service.accounts.dao.repository.UserRepository;
import org.bars.hackathon.service.accounts.dto.registration.RegistrationRequestDTO;
import org.bars.hackathon.service.accounts.validators.RegistrationRequestDTOValidator;
import org.bars.hackathon.service.admin.dto.UserCreateDTO;
import org.bars.hackathon.service.admin.dto.UserResponseDTO;
import org.bars.hackathon.service.admin.dto.mappers.UserResponseMapper;
import org.bars.hackathon.service.admin.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Имплементация {@link AdminService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final UserEntityMapper userMapper;

    private final UserResponseMapper userResponseMapper;

    private final RegistrationRequestDTOValidator registrationRequestDTOValidator;

    @Override
    public List<UserResponseDTO> getUsers(UserEntity userEntity) {
        return userRepository.findAll().stream()
                .map(userResponseMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createUser(RegistrationRequestDTO requestDTO) throws BindException {
        validateRegistrationRequest(requestDTO);
        UserEntity user = userMapper.map(requestDTO.getLogin(), requestDTO.getUserRole(), requestDTO.getPassword());
        SettingsEntity settingsEntity = new SettingsEntity();
        settingsEntity.setUser(user);
        user.setSettings(settingsEntity);
//        log.info(user.toString());
        userRepository.save(user);
//        log.info(user.toString());
    }

    private void validateRegistrationRequest(RegistrationRequestDTO requestDTO) throws BindException {
        DataBinder binder = new DataBinder(requestDTO);
        binder.addValidators(registrationRequestDTOValidator);
        binder.validate();
        BindingResult bindingResult = binder.getBindingResult();
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
    }
}

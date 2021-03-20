package org.bars.hackathon.service.accounts.service.impl;

import com.google.common.primitives.Booleans;
import lombok.RequiredArgsConstructor;
import org.bars.hackathon.consts.Message;
import org.bars.hackathon.handlers.exception.BusinessRuntimeException;
import org.bars.hackathon.security.jwt.JwtTokenProvider;
import org.bars.hackathon.service.accounts.dao.entity.SettingsEntity;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;
import org.bars.hackathon.service.accounts.dao.entity.mapper.UserEntityMapper;
import org.bars.hackathon.service.accounts.dao.repository.SettingsRepository;
import org.bars.hackathon.service.accounts.dao.repository.UserRepository;
import org.bars.hackathon.service.accounts.dto.auth.AuthResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.AccountInfoResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.AccountSettingsResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.mapper.AccountInfoResponseDTOMapper;
import org.bars.hackathon.service.accounts.dto.info.mapper.AccountSettingsResponseDTOMapper;
import org.bars.hackathon.service.accounts.dto.registration.RegistrationRequestDTO;
import org.bars.hackathon.service.accounts.mappers.AuthResponseDTOMapper;
import org.bars.hackathon.service.accounts.service.AccountService;
import org.bars.hackathon.service.accounts.validators.RegistrationRequestDTOValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

/**
 * Сервис для работы с пользователями
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    private final RegistrationRequestDTOValidator registrationRequestDTOValidator;

    private final UserEntityMapper userMapper;

    private final AccountInfoResponseDTOMapper accountInfoResponseDTOMapper;

    private final AccountSettingsResponseDTOMapper accountSettingsResponseDTOMapper;

    @Override
    public AuthResponseDTO authUser(String login, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        UserEntity user = userRepository.findOneByLogin(login)
                .orElseThrow(() -> new BusinessRuntimeException(Message.AUTH_ERROR.getMessage()));
        UserRoleEnum role = user.getUserRole();
        String token = jwtTokenProvider.createToken(user.getLogin(), role.name());
        return new AuthResponseDTOMapper().map(token, user);
    }

    @Override
    @Transactional
    public void registerUser(RegistrationRequestDTO requestDTO) throws BindException {
//        validateRegistrationRequest(requestDTO);
        UserEntity user = userMapper.map(requestDTO.getLogin(), UserRoleEnum.SPM_USER, requestDTO.getPassword());
        SettingsEntity settingsEntity = new SettingsEntity();
        settingsEntity.setUser(user);
        user.setSettings(settingsEntity);
        userRepository.save(user);
    }

    @Override
    public AccountInfoResponseDTO getAccountInfo(UserEntity user) {
        return accountInfoResponseDTOMapper.map(user);
    }

    @Override
    public AccountSettingsResponseDTO getSettings(UserEntity user) {
        return accountSettingsResponseDTOMapper.map(user);
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
package org.bars.hackathon.service.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dao.repository.UserRepository;
import org.bars.hackathon.service.admin.dto.UserCreateDTO;
import org.bars.hackathon.service.admin.dto.UserResponseDTO;
import org.bars.hackathon.service.admin.dto.mappers.UserResponseMapper;
import org.bars.hackathon.service.admin.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Имплементация {@link AdminService}
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final UserResponseMapper userResponseMapper;

    @Override
    public List<UserResponseDTO> getUsers(UserEntity userEntity) {
        return userRepository.findAll().stream()
                .map(userResponseMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public boolean createUser(UserEntity userEntity, UserCreateDTO userCreate) {
        UserEntity user = UserEntity.builder()
                .login(userCreate.getUsername())
                .password(userCreate.getPassword())
                .name("her")
                .userRole(userCreate.getUserRole())
                .surname("herr")
                .build();
        userRepository.save(user);
        return true;
    }


}

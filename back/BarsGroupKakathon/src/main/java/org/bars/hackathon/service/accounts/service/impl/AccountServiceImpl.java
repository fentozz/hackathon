package org.bars.hackathon.service.accounts.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bars.hackathon.consts.Message;
import org.bars.hackathon.handlers.exception.BusinessRuntimeException;
import org.bars.hackathon.security.jwt.JwtTokenProvider;
import org.bars.hackathon.service.accounts.dao.entity.FileEntity;
import org.bars.hackathon.service.accounts.dao.entity.SettingsEntity;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;
import org.bars.hackathon.service.accounts.dao.entity.mapper.UserEntityMapper;
import org.bars.hackathon.service.accounts.dao.repository.FileRepository;
import org.bars.hackathon.service.accounts.dao.repository.SettingsRepository;
import org.bars.hackathon.service.accounts.dao.repository.UserRepository;
import org.bars.hackathon.service.accounts.dto.auth.AuthResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.AccountInfoResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.AccountSettingsResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.mapper.AccountInfoResponseDTOMapper;
import org.bars.hackathon.service.accounts.dto.info.mapper.AccountSettingsResponseDTOMapper;
import org.bars.hackathon.service.accounts.dto.registration.RegistrationRequestDTO;
import org.bars.hackathon.service.accounts.dto.upload.FileDTO;
import org.bars.hackathon.service.accounts.dto.upload.FileFormatEnum;
import org.bars.hackathon.service.accounts.dto.upload.UploadFileDTO;
import org.bars.hackathon.service.accounts.mappers.AuthResponseDTOMapper;
import org.bars.hackathon.service.accounts.mappers.FileDTOMapper;
import org.bars.hackathon.service.accounts.service.AccountService;
import org.bars.hackathon.service.accounts.validators.RegistrationRequestDTOValidator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.bars.hackathon.consts.Message.UPLOAD_FAILED_BY_OVERLOAD;

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

    private final FileDTOMapper fileDTOMapper;

    private final FileRepository fileRepository;

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
    public List<FileDTO> getUserFiles(UserEntity user) {
        return fileRepository.findAllByUser(user).stream()
                .map(fileDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public AccountInfoResponseDTO getAccountInfo(UserEntity user) {
        return accountInfoResponseDTOMapper.map(user);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void uploadFile(UserEntity userEntity, UploadFileDTO uploadFileDTO) {
        MultipartFile multiFile = uploadFileDTO.getMultipartFile();
        checkMaxFileSize(userEntity, multiFile);
        String fileName = UUID.randomUUID().toString();
        int extensionNum = multiFile.getOriginalFilename().lastIndexOf(".");
        String extension = multiFile.getOriginalFilename().substring(extensionNum);
        FileFormatEnum fileFormatEnum = getFileExtension(extension);
        File file = new File(System.getProperty("user.dir") + File.separator + fileName + extension);
        multiFile.transferTo(file);
        FileEntity fileEntity = createFileEntity(userEntity, uploadFileDTO, multiFile, fileFormatEnum, file);
        fileRepository.save(fileEntity);
    }

    private FileFormatEnum getFileExtension(String extension) {
        FileFormatEnum fileFormatEnum = Arrays.stream(FileFormatEnum.values())
                .filter(format -> format.getExtensions().contains(extension))
                .findFirst()
                .orElse(FileFormatEnum.UNDEFINED);
        if (fileFormatEnum == FileFormatEnum.UNDEFINED) {
            throw new BusinessRuntimeException(Message.UPLOAD_FAILED_BY_UNDEFINED_EXTENSION.getMessage());
        }
        return fileFormatEnum;
    }

    private void checkMaxFileSize(UserEntity userEntity, MultipartFile multiFile) {
        List<FileEntity> allByUser = fileRepository.findAllByUser(userEntity);
        Long reduce = allByUser.stream().map(FileEntity::getFileSize).reduce(0L, Long::sum);
        if (reduce + multiFile.getSize() > userEntity.getFileSizeStorage()) {
            throw new BusinessRuntimeException(UPLOAD_FAILED_BY_OVERLOAD.getMessage());
        }
    }

    private FileEntity createFileEntity(UserEntity userEntity, UploadFileDTO uploadFileDTO, MultipartFile multiFile, FileFormatEnum fileFormatEnum, File file) {
        return FileEntity.builder()
                .filePath(file.getPath())
                .fileSize(multiFile.getSize())
                .fileFormat(fileFormatEnum)
                .name(uploadFileDTO.getFileName())
                .user(userEntity)
                .build();
    }

    @Override
    public AccountSettingsResponseDTO getSettings(UserEntity user) {
        return accountSettingsResponseDTOMapper.map(user);
    }
}
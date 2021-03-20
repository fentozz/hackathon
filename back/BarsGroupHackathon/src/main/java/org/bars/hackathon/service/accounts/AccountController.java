package org.bars.hackathon.service.accounts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bars.hackathon.consts.Message;
import org.bars.hackathon.handlers.dto.SuccessMessageDTO;
import org.bars.hackathon.helpers.HttpRequestHelper;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dto.auth.AuthRequestDTO;
import org.bars.hackathon.service.accounts.dto.auth.AuthResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.AccountInfoResponseDTO;
import org.bars.hackathon.service.accounts.dto.info.AccountSettingsResponseDTO;
import org.bars.hackathon.service.accounts.dto.registration.RegistrationRequestDTO;
import org.bars.hackathon.service.accounts.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.bars.hackathon.consts.Paths.API_V2;


/**
 * Контроллер для работы с пользовательскими учетными записями
 */
@Tag(name = "Сервис для работы с пользовательскими учетными записями")
@RestController
@RequiredArgsConstructor
@RequestMapping(API_V2 + "/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;

    private final HttpRequestHelper httpRequestHelper;

    @PostMapping("/login")
    @SecurityRequirements
    @Operation(summary = "Авторизовать пользователя", description = "Аутентификации пользователя по логину и паролю")
    @ApiResponse(responseCode = "200", description = "Успешная аутентификации, помещаем полученный токен в заголовок token",
            content = {@Content(schema = @Schema(implementation = AuthResponseDTO.class))})
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO requestDTO) {
        String username = requestDTO.getLogin();
        String password = requestDTO.getPassword();
        return ResponseEntity.ok(accountService.authUser(username, password));
    }

    @GetMapping("/logout")
    @SecurityRequirement(name = "token")
    @Operation(summary = "Выйти из системы", description = "Разъединить авторизационную сессию")
    @ApiResponse(responseCode = "200", description = "Авторизационная сессия завершена",
            content = {@Content(schema = @Schema(implementation = SuccessMessageDTO.class))})
    public ResponseEntity<SuccessMessageDTO> logout(HttpServletRequest httpRequest) {
        httpRequestHelper.terminateSession(httpRequest);
        return ResponseEntity.ok(new SuccessMessageDTO(Message.LOGOUT_SUCCESS.getMessage()));
    }

    @GetMapping("/info")
    @Operation(summary = "Вывод информации о пользователе", description = "Получить информацию о текущем пользователе")
    @ApiResponse(responseCode = "200", description = "Информация о пользователе",
            content = {@Content(schema = @Schema(implementation = AccountInfoResponseDTO.class))})
    public AccountInfoResponseDTO getInfo(HttpServletRequest request) {
        UserEntity user = httpRequestHelper.getUser(request);
        return accountService.getAccountInfo(user);
    }

    @GetMapping("/settings")
    @Operation(summary = "Получить данные для настройки пользовательского интерфейса", description = "Получить данные для настройки пользовательского интерфейса")
    @ApiResponse(responseCode = "200", description = "Данные для настройки",
            content = {@Content(schema = @Schema(implementation = AccountSettingsResponseDTO.class))})
    public AccountSettingsResponseDTO getSettings(HttpServletRequest request) {
        UserEntity user = httpRequestHelper.getUser(request);
        return accountService.getSettings(user);
    }
}
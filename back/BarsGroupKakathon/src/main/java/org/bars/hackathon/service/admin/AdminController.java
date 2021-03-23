package org.bars.hackathon.service.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bars.hackathon.consts.Message;
import org.bars.hackathon.handlers.dto.SuccessMessageDTO;
import org.bars.hackathon.helpers.HttpRequestHelper;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;
import org.bars.hackathon.service.accounts.dto.registration.RegistrationRequestDTO;
import org.bars.hackathon.service.admin.dto.RoleDTO;
import org.bars.hackathon.service.admin.dto.UserResponseDTO;
import org.bars.hackathon.service.admin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.bars.hackathon.consts.Paths.API_V2;

/**
 * Контроллер для панели администратора
 */
@Tag(name = "Сервис для работы с панелью администратора")
@SecurityRequirement(name = "token")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(API_V2 + "/admin")
@PreAuthorize(value = "hasAuthority(T(org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum).SPM_ADMIN.toString())")
public class AdminController {

	private final HttpRequestHelper requestHelper;

	private final AdminService adminService;

	@GetMapping("/accounts")
	@Operation(summary = "Получить всех пользователей", description = "Получить всех пользователей")
	@ApiResponse(responseCode = "200", description = "Пользователи АС",
			content = {@Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))})
	public List<UserResponseDTO> getAccounts(HttpServletRequest request) {
		UserEntity user = requestHelper.getUser(request);
		return adminService.getUsers(user);
	}

	@PostMapping("/register")
	@ApiResponse(responseCode = "200", description = "Пользователь успешно зарегестрирован",
			content = {@Content(schema = @Schema(implementation = SuccessMessageDTO.class))})
	public ResponseEntity<SuccessMessageDTO> createUser(HttpServletRequest request, @RequestBody RegistrationRequestDTO registrationRequest) throws BindException {
		log.info(registrationRequest.toString());
		UserEntity user = requestHelper.getUser(request);
//		log.info(user.toString());
		adminService.createUser(registrationRequest);
		return ResponseEntity.ok(new SuccessMessageDTO(Message.REGISTER_SUCCESS.getMessage()));
	}

	@GetMapping("/roles")
	@Operation(summary = "Получить список ролей", description = "Получить список ролей")
	@ApiResponse(responseCode = "200", description = "Пользователи АС",
			content = {@Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))})
	public List<RoleDTO> getRoles(HttpServletRequest request) {
		UserEntity user = requestHelper.getUser(request);
		return Arrays.stream(UserRoleEnum.values())
				.map(role -> new RoleDTO(role.name(), role.getDescription()))
				.collect(Collectors.toList());
	}
}

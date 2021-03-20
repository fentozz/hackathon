package org.bars.hackathon.service.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bars.hackathon.helpers.HttpRequestHelper;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.admin.dto.UserCreateDTO;
import org.bars.hackathon.service.admin.dto.UserResponseDTO;
import org.bars.hackathon.service.admin.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.bars.hackathon.consts.Paths.API_V2;

/**
 * Контроллер для панели администратора
 */
@Tag(name = "Сервис для работы с панелью администратора")
//@SecurityRequirement(name = "token")
@RestController
@RequiredArgsConstructor
@RequestMapping(API_V2 + "/admin")
//@PreAuthorize(value = "hasAuthority(T(org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum).SPM_ADMIN.toString())")
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
	public boolean createUser(HttpServletRequest request, @RequestBody UserCreateDTO userCreateDTO) {
//        UserEntity user = requestHelper.getUser(request);
		return adminService.createUser(null, userCreateDTO);
	}
}

package org.bars.hackathon.service.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {

	private String username;

	private String password;

	private UserRoleEnum userRole;
}

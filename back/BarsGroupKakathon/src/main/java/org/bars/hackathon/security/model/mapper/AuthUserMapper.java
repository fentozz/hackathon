package org.bars.hackathon.security.model.mapper;

import com.google.common.collect.Lists;
import org.bars.hackathon.security.model.AuthUser;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Компонент для маппинга {@link AuthUser}
 */
@Component
public class AuthUserMapper {

    public AuthUser map(UserEntity user) {
        if (user == null) {
            return null;
        }
        return AuthUser.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .authorities(mapToGrantedAuthorities(user.getUserRole()))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(UserRoleEnum role) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Lists.newArrayList(authority);
    }
}
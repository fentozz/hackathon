package org.bars.hackathon.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Модель пользователя в spring security context
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUser implements UserDetails {

    private static final long serialVersionUID = -3684917393035645779L;

    /**
     * Login для авторизации
     */
    private String login;

    /**
     * Пароль для авторизации
     */
    private String password;

    /**
     * Роль пользователя
     */
    private UserRoleEnum role;

    /**
     * Параметры авторизации
     */
    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
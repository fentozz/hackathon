package org.bars.hackathon.security.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.bars.hackathon.security.model.AuthUser;
import org.bars.hackathon.service.accounts.dao.entity.UserEntity;
import org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum;
import org.bars.hackathon.service.accounts.dao.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Сервис для выборки пользователя в spring security context
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = userRepository.findOneByLogin(login)
                .orElse(null);
        return map(user);
    }

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
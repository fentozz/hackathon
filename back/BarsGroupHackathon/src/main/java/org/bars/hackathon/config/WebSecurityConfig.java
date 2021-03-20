package org.bars.hackathon.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;

import static org.bars.hackathon.consts.Paths.ADMIN_URLS;
import static org.bars.hackathon.consts.Paths.IGNORED_SECURE_URLS;
import static org.bars.hackathon.consts.Paths.SECURE_URLS;
import static org.bars.hackathon.service.accounts.dao.entity.enums.UserRoleEnum.SPM_ADMIN;

/**
 * Конфигурация политики безопасности доступа к endpoint
 */
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final AuthenticationEntryPoint entryPoint;

	private final SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> adapter;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.httpBasic().disable()
				.anonymous().disable()
				.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(entryPoint)
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(ADMIN_URLS).hasRole(SPM_ADMIN.name())
				.and()
				.authorizeRequests()
				.antMatchers(SECURE_URLS).authenticated()
				.and()
				.apply(adapter);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(IGNORED_SECURE_URLS);
	}
}

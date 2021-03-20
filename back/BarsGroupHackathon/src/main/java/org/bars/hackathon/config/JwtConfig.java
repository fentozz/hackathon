package org.bars.hackathon.config;

import lombok.RequiredArgsConstructor;
import org.bars.hackathon.security.jwt.JwtTokenFilter;
import org.bars.hackathon.security.jwt.JwtTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void configure(HttpSecurity httpSecurity) {
		JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
		httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}

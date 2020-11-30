package com.mvfinapp.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

	private PasswordEncoder passwordEncoder;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		
		if(passwordEncoder == null) {
			passwordEncoder = new BCryptPasswordEncoder();
		}
		return passwordEncoder;
	}

	public String encode(final String password) {
		
		return getPasswordEncoder().encode(password);
	}

	public boolean matches(final String currentPassword, final String password) {
		
		return getPasswordEncoder().matches(currentPassword, password);
	}

	public boolean noMatches(final String currentPassword, final String password) {
		
		return !matches(currentPassword, password);
	}
}
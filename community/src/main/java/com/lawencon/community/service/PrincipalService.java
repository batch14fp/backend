package com.lawencon.community.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalService {
	public String getAuthPrincipal() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth == null || auth.getPrincipal() == null)
			throw new RuntimeException("Invalid Login");
		return String.valueOf( auth.getPrincipal().toString());
	}
}

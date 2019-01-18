package io.github.thang86.utilities;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

/**
*  AuthUtil.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-28    ThangTX     Create
*/

public class AuthUtil {
	
	public static CurrentUser getCurrentUser(){
		return (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static void addRoleAtRuntime(Role role){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());

		if(updatedAuthorities.contains(new SimpleGrantedAuthority(role.name())))
			return;

		updatedAuthorities.add(new SimpleGrantedAuthority(role.name()));

		CurrentUser updatedUser = getCurrentUser();
		updatedUser.getRole().add(role);

		Authentication newAuth = new PreAuthenticatedAuthenticationToken(updatedUser, auth.getCredentials(), updatedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}

	public static void updateOrders(Integer newCount){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		CurrentUser updatedUser = getCurrentUser();
		updatedUser.setOrdersCount(newCount);

		Authentication newAuth = new PreAuthenticatedAuthenticationToken(updatedUser, auth.getCredentials(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
}

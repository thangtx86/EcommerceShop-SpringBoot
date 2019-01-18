package io.github.thang86.auth;



import io.github.thang86.entities.User;
import io.github.thang86.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
*  CurrentUserDetailsService.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-11-13    ThangTX     Create
*/

@Service
public class CurrentUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Không tìm thấy tên người dùng này [%s]", username)));								
		return new CurrentUser(user, userService.getUserOrdersCount(user.getId()));
	}
}

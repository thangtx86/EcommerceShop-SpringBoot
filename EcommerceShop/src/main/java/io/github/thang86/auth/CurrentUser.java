package io.github.thang86.auth;




import antlr.StringUtils;
import io.github.thang86.entities.User;
import io.github.thang86.enums.Role;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

/**
*  CurrentUser.java
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

public class CurrentUser extends org.springframework.security.core.userdetails.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;

	private Integer ordersCount;

	public CurrentUser(User user, Integer ordersCount) {
		super(user.getUsername(), user.getPasswordHash(),
			AuthorityUtils.commaSeparatedStringToAuthorityList(
					StringUtils.stripFrontBack(user.getRoles().toString(), "[", "]" )
			)
		);
		this.user = user;
		this.ordersCount = ordersCount;
	}

	public User getUser() {
		return user;
	}


	public Integer getOrdersCount() {
		return ordersCount;
	}

	public void setOrdersCount(Integer ordersCount) {
		this.ordersCount = ordersCount;
	}

	public long getId() {
		return user.getId();
	}

	public Collection<Role> getRole() {
		return user.getRoles();
	}
}

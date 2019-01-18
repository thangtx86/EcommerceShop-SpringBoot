package io.github.thang86.service;


import io.github.thang86.entities.User;
import io.github.thang86.forms.DemoteAdminForm;
import io.github.thang86.forms.PromoteAdminForm;
import io.github.thang86.forms.UserCreateForm;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
*  UserService.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-20    ThangTX     Create
*/

public interface UserService {

	Optional<User> getUserById(Long id);

	Optional<User> getUserByEmail(String email);

	Optional<User> getUserByUsername(String email);

	List getAllActiveSubordinates(Long id);

	Integer getUserOrdersCount(Long Id);

	Collection<User> getAllUsers();

	User register(UserCreateForm form);

	User promoteAdmin(PromoteAdminForm form, User superior);

	User demoteAdmin(DemoteAdminForm form, User superior);

}

package io.github.thang86.validators;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.Admin;
import io.github.thang86.entities.User;
import io.github.thang86.enums.Role;
import io.github.thang86.forms.DemoteAdminForm;
import io.github.thang86.service.UserService;
import io.github.thang86.utilities.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

/**
*  DemoteAdminFormValidator.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2019-01-02    ThangTX     Create
*/

@Component
public class DemoteAdminFormValidator implements Validator {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(DemoteAdminForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		DemoteAdminForm form = (DemoteAdminForm) target;
	
		if (errors.hasErrors())
			return;

		Optional<User> userOptional = userService.getUserByUsername(form.getUsername());

		if (!userOptional.isPresent()) {
			errors.rejectValue("username", "msg.UsernameNotFound");
			return;
		}

		User user = userOptional.get();

		if (!user.getRoles().contains(Role.ADMIN)) {
			errors.rejectValue("username", "msg.NotAdmin");
			return;
		}

		CurrentUser currentUser = AuthUtil.getCurrentUser();

		if (user.getId().equals(currentUser.getUser().getId())) {
			errors.rejectValue("username", "msg.PromoteSelf");
		}

		if (user.getAdmin().getSuperior().getId() != currentUser.getId()) {
			
			Admin admin = user.getAdmin().getSuperior();
			while(true)
			{
				if(admin.getId() == currentUser.getId())
					break;

				
				else if(Objects.equals(admin.getId(), admin.getSuperior().getId())) {
					errors.rejectValue("username", "msg.NotYourSub");
					break;
				}

				admin = admin.getSuperior();
			}
		}
	}
}

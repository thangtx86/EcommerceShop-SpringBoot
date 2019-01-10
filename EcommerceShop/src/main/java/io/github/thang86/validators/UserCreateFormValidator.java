package io.github.thang86.validators;


import io.github.thang86.repository.UserRepository;
import io.github.thang86.forms.UserCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
*  UserCreateFormValidator.java
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
public class UserCreateFormValidator implements Validator {
	@Autowired
	private UserRepository userRepository;


	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UserCreateForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserCreateForm form = (UserCreateForm) target;
		validatePasswords(errors, form);
		validateUsername(errors, form);
		validateEmail(errors, form);
	}

	private void validatePasswords(Errors errors, UserCreateForm form) {
		if (!form.getPassword().equals(form.getPasswordConfirm())) {
			errors.rejectValue("password", "msg.PasswordNotMatch");
		}
	}

	private void validateUsername(Errors errors, UserCreateForm form) {
		//This to avoid querying using not valid or (and especially empty value "" ),
		// empty value cause findOne to return more than one element causing an exception which is so stupid :'D, I hate this.
		if(errors.hasFieldErrors("username"))
			return;

		//Actual Validation
		if (userRepository.findOneByUsername(form.getUsername()).isPresent()) {
			errors.rejectValue("username", "msg.DuplicateUsername");
		}
	}

	private void validateEmail(Errors errors, UserCreateForm form) {
		//This to avoid querying using not valid or (and especially empty value "" ),
		// empty value cause findOne to return more than one element causing an exception which is so stupid :'D, I hate this.
		if(errors.hasFieldErrors("email"))
			return;

		if (userRepository.findOneByEmail(form.getEmail()).isPresent()) {
			errors.rejectValue("email", "msg.DuplicateEmail");
		}
	}
}

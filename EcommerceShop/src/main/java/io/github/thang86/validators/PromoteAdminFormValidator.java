package io.github.thang86.validators;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.User;
import io.github.thang86.enums.Role;
import io.github.thang86.forms.PromoteAdminForm;
import io.github.thang86.service.AuthService;
import io.github.thang86.service.UserService;
import io.github.thang86.utilities.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
*  PromoteAdminFormValidator.java
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
public class PromoteAdminFormValidator implements Validator {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(PromoteAdminForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PromoteAdminForm form = (PromoteAdminForm) target;

        //Avoid Querying DB if there is an error already.
        if(errors.hasErrors())
            return;

        Optional<User> userOptional = userService.getUserByUsername(form.getUsername());

        if(!userOptional.isPresent()) {
            errors.rejectValue("username", "NotValid");
            return;
        }

        User user = userOptional.get();

        if(user.getRoles().contains(Role.ADMIN)) {
            errors.rejectValue("username", "msg.AlreadyAdmin");
        }

        CurrentUser currentUser = AuthUtil.getCurrentUser();

        if(user.getId().equals(currentUser.getUser().getId())) {
            errors.rejectValue("username", "msg.PromoteSelf");
        }
    }
}

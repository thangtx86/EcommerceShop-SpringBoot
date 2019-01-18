package io.github.thang86.validators;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.Store;
import io.github.thang86.entities.User;
import io.github.thang86.forms.AddStoreCollaboratorForm;
import io.github.thang86.service.AuthService;
import io.github.thang86.service.StoreService;
import io.github.thang86.service.UserService;
import io.github.thang86.utilities.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
*  AddCollaboratorFormValidator.java
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
public class AddCollaboratorFormValidator implements Validator {
    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(AddStoreCollaboratorForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddStoreCollaboratorForm form = (AddStoreCollaboratorForm) target;

        if(errors.hasErrors())
            return;

        Optional<User> userOptional = userService.getUserByUsername(form.getUsername());
        if(!userOptional.isPresent()) {
            errors.rejectValue("username", "msg.UsernameNotFound");
            return;
        }

        Optional<Store> storeOptional = storeService.getStoreById(form.getStoreId());
        if(!storeOptional.isPresent()) {
            errors.rejectValue("storeId", "msg.NotValid");
            return;
        }

        User user = userOptional.get();
        Store store = storeOptional.get();

        CurrentUser currentUser = AuthUtil.getCurrentUser();
        if(!authService.canAccessStore(store, currentUser)) {
            errors.rejectValue("storeId", "msg.NotAuthorized");
            return;
        }
        if(store.getCollaborators().contains(user.getStoreOwner())) {
            errors.rejectValue("username", "msg.DuplicateCollaborator");
            return;
        }
        if(user.getId()==currentUser.getUser().getId()) {
            errors.rejectValue("username", "msg.DuplicateStoreOwner");
            return;
        }
    }
}

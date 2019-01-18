package io.github.thang86.viewmodels;


import io.github.thang86.entities.User;
import io.github.thang86.forms.DemoteAdminForm;
import io.github.thang86.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
*  DemoteAdminViewModel.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-13    ThangTX     Create
*/
@Component
public class DemoteAdminViewModel {
    @Autowired
    private UserService userService;

    public HashMap<String, Object> create(DemoteAdminForm form, Long userId) {
        HashMap<String, Object> model = new HashMap<>();
        List<User> admins =  userService.getAllActiveSubordinates(userId);
        model.put("form"    , form);
        model.put("admins"  , admins);
        return model;
    }
}

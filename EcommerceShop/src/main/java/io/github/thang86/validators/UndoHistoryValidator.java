package io.github.thang86.validators;


import io.github.thang86.auth.CurrentUser;
import io.github.thang86.entities.StoreHistory;
import io.github.thang86.enums.StoreHistoryStatus;
import io.github.thang86.forms.UndoHistoryForm;
import io.github.thang86.service.AuthService;
import io.github.thang86.service.StoreHistoryService;
import io.github.thang86.utilities.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

/**
*  UndoHistoryValidator.java
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
public class UndoHistoryValidator implements Validator {

	@Autowired
	StoreHistoryService storeHistoryService;

	@Autowired
	AuthService authService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UndoHistoryForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UndoHistoryForm undoHistoryForm = (UndoHistoryForm) target;

		
		if(errors.hasErrors())
			return;

		Optional<StoreHistory> storeHistoryOptional = storeHistoryService.getById(undoHistoryForm.getId());

		if(!storeHistoryOptional.isPresent()) {
			errors.rejectValue("Id", "NotValid", "Lịch sử không tồn tại!");
			return;
		}

		StoreHistory storeHistory = storeHistoryOptional.get();
		if(storeHistory.getStatus() != StoreHistoryStatus.UNDOABLE){
			errors.rejectValue("Id", "NotValid");
			return;
		}


		CurrentUser currentUser = AuthUtil.getCurrentUser();
		if(!authService.canAccessStore(storeHistory.getStore(), currentUser)){
			errors.rejectValue("Id", "msg.NotAuthorized", "Bạn không được ủy quền để làm điều này!");
		}

	}
}

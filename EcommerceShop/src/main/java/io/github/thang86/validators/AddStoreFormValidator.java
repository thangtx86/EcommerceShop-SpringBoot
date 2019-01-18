package io.github.thang86.validators;


import io.github.thang86.forms.AddStoreForm;
import io.github.thang86.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
*  AddStoreFormValidator.java
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
public class AddStoreFormValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AddStoreForm.class);
	}

	@Autowired
	private StoreService storeService;

	@Override
	public void validate(Object target, Errors errors) {
		AddStoreForm form = (AddStoreForm) target;
		if(form.getPhysical())
			validatePhysical(errors, form);

		validateUniqueName(errors, form);

	}

	private void validatePhysical(Errors errors,AddStoreForm form)
	{
		if(form.getAddress().isEmpty())
			errors.rejectValue("address","NotEmpty");

		if(form.getAddress().length() < 2 || form.getAddress().length() > 200)
			errors.rejectValue("address","msg.AddressSizeRange");
	}

	private void validateUniqueName(Errors errors,AddStoreForm form)
	{
		if(errors.hasFieldErrors("name"))
			return;

		if(storeService.getStoreByName(form.getName()).isPresent()){
			errors.rejectValue("name","msg.DuplicateStoreNameSameOwner");
		}
	}

}

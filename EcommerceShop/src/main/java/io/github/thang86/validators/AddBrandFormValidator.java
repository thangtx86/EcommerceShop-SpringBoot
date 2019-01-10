package io.github.thang86.validators;


import io.github.thang86.repository.BrandRepository;
import io.github.thang86.forms.AddBrandForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
*  AddBrandFormValidator.java
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
public class AddBrandFormValidator implements Validator{
   @Autowired
    private BrandRepository brandRepository;

   @Override
    public boolean supports(Class<?> clazz)
   {
       return clazz.equals(AddBrandForm.class);
   }
    public void validate(Object target,Errors errors)
   {
       AddBrandForm form =(AddBrandForm) target;
       validateName(errors,form);
   }
   private void validateName(Errors errors,AddBrandForm form)
   {
       if(errors.hasFieldErrors("name"))
           return;

       if(brandRepository.findOneByName(form.getName()).isPresent()) {
                errors.rejectValue("name","msg.DuplicateBrandName");
       }
   }
}

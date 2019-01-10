package io.github.thang86.validators;


import io.github.thang86.repository.CompanyRepository;
import io.github.thang86.forms.AddCompanyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
*  AddCompanyFormValidator.java
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
public class AddCompanyFormValidator implements Validator{
   @Autowired
    private CompanyRepository companyRepository;

   @Override
    public boolean supports(Class<?> clazz)
   {
       return clazz.equals(AddCompanyForm.class);
   }
    public void validate(Object target,Errors errors)
   {
       AddCompanyForm form =(AddCompanyForm) target;
       validateName(errors,form);
   }
   private void validateName(Errors errors,AddCompanyForm form)
   {
       if(errors.hasFieldErrors("name"))
           return;

       if(companyRepository.findOneByName(form.getName()).isPresent()) {
                errors.rejectValue("name","msg.DuplicateCompanyName");
       }
   }
}

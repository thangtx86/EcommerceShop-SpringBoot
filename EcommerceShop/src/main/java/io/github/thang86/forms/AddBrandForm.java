package io.github.thang86.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
*  AddBrandForm.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-12    ThangTX     Create
*/

public class AddBrandForm {

    @NotEmpty
    @Size(min = 2, max = 40)
    private String name="";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

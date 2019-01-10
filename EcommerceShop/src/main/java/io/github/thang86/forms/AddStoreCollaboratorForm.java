package io.github.thang86.forms;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
*  AddStoreCollaboratorForm.java
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

@Component
public class AddStoreCollaboratorForm {
    @NotNull
    private Long storeId;

    @NotEmpty
    @Length(min = 3, max = 150)
    private String username;


    public AddStoreCollaboratorForm() {
    }

    public AddStoreCollaboratorForm(Long storeId, String username) {
        this.storeId = storeId;
        this.username = username;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

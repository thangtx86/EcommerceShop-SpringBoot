package io.github.thang86.forms;

import javax.validation.constraints.NotNull;

/**
*  UndoHistoryForm.java
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


public class UndoHistoryForm {

    @NotNull
    private Long Id;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}

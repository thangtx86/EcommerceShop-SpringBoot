package io.github.thang86.entities;


import io.github.thang86.enums.StoreHistoryType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Date;

/**
*  StoreCollabHistory.java
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


@Entity
@PrimaryKeyJoinColumn(referencedColumnName="id")
@Table(name="storeCollabHistory")
public class StoreCollabHistory extends StoreHistory {
    @ManyToOne
    private User collab;

	public StoreCollabHistory(User user, Store store, String message, Date dateTime, StoreHistoryType type, User collab) {
		super(user, store, message, dateTime, type);
		this.collab = collab;
	}

	public User getCollab() {
		return collab;
	}

	public void setCollab(User collab) {
		this.collab = collab;
	}

	public StoreCollabHistory() {
	}
}

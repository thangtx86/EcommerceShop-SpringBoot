package io.github.thang86.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
*  VirtualProduct.java
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
public class VirtualProduct extends Product {
	private String serial;
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
}


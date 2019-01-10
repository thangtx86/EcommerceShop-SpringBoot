package io.github.thang86.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;


/**
*  PhysicalProduct.java
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
public class PhysicalProduct extends Product {
	private float weight;
	private float length;
	private float width;
	private float height;

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
}


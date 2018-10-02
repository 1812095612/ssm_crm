package com.qiaosoftware.crm.entity;

import java.io.Serializable;

public abstract class IdEntity implements Serializable{

	public Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}

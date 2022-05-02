package com.authentication.model;

import java.io.Serializable;

public class Good implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private User owner;
	
	public Good(String name, User owner) {
		this.name = name;
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	

}

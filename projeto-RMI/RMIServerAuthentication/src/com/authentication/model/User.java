package com.authentication.model;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String name;
	private String password;
	private int permission;
	
	public User(String login, String name, String password, int permission) {
		this.login = login;
		this.name = name;
		this.password = password;
		this.permission = permission;
	}
	
	public User(String login, String name, String password) {
		this.login = login;
		this.name = name;
		this.password = password;
	}
	
	public User(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public User() {
	}
	
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getPermission() {
		return this.permission;
	}
	
	public void setPermission(int permission) {
		this.permission = permission;
	}
}
package com.authentication.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.authentication.model.User;

public class AuthServer extends UnicastRemoteObject implements AuthServerInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<User> users = new ArrayList<>();

	public AuthServer() throws RemoteException{
		super();
	}

	public int validateLogin(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return 0; // deu certo
	}

	public int login(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int validateRegistration(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return 0; // deu certo
	}

	@Override
	public int register(User user) throws RemoteException {
		users.add(user);
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int changePermission(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User findByLogin(String login) throws RemoteException {
		for (User u : users)
			if (u.getLogin().equals(login)) return u;
		
		return null;
	}
	
	public ArrayList<User> listUsers() throws RemoteException {
		return users;
	}
}

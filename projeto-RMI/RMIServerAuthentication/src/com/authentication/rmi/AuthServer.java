package com.authentication.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.authentication.model.Good;
import com.authentication.model.User;

public class AuthServer extends UnicastRemoteObject implements AuthServerInterface{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<User> users = new ArrayList<>();
	private ArrayList<Good> goods = new ArrayList<>();

	public AuthServer() throws RemoteException{
		super();
	}

	public void insertUser(User user) throws RemoteException {
		users.add(user);
	}

	public void updateUserPermission(User user, int newPermission) throws RemoteException {
		findUserByLogin(user.getLogin()).setPermission(newPermission);
	}

	public User findUserByLogin(String login) throws RemoteException {
		for (User u : users)
			if (u.getLogin().equals(login)) return u;
		
		return null;
	}
	
	public ArrayList<User> listUsers() throws RemoteException {
		return users;
	}
	
	public ArrayList<Good> listGoods() throws RemoteException {
		return goods;
	}
	
	public ArrayList<Good> listGoodsByOwner(User owner) throws RemoteException {
		ArrayList<Good> goodsResult = new ArrayList<>();
		for (Good g : goods)
			if (g.getOwner().getLogin().equals(owner.getLogin())) goodsResult.add(g);
		return goodsResult;
	}
	
	public void insertGood(Good good) throws RemoteException {
		goods.add(good);
	}
}

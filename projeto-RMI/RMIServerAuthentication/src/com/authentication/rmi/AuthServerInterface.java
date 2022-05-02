package com.authentication.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.authentication.model.Good;
import com.authentication.model.User;

public interface AuthServerInterface extends Remote{
	
	public void insertUser(User user) throws RemoteException;
	public void updateUserPermission (User user, int newPermission) throws RemoteException;
	public User findUserByLogin (String login) throws RemoteException;
	public ArrayList<User> listUsers () throws RemoteException;
	public ArrayList<Good> listGoods() throws RemoteException;
	public ArrayList<Good> listGoodsByOwner(User owner) throws RemoteException;
	public void insertGood(Good good) throws RemoteException;
	
}

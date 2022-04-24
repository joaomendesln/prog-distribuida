package com.authentication.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Scanner;

import com.authentication.model.User;

public class Main {

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		
		System.setProperty("java.rmi.server.hostname","127.0.0.1");
		
		AuthServerInterface server = new AuthServer();
	
		LocateRegistry.createRegistry(1099);
		
		Naming.rebind("rmi://127.0.0.1:1099/AuthServer", server);
		
		System.out.println("Server Starterd.");
	
		
		Scanner sc = new Scanner(System.in);
		int op = -1;
		
		try{
			while (op != 0) {
				switch(op) {
				case 1:
					System.out.println("----- LISTAR USUÁRIOS -----");
					ArrayList<User> users = server.listUsers();
					for(User u : users) System.out.println(u.getLogin() + " - " + u.getName());
					break;
				case 2:
					System.out.println("----- MUDAR PERMISSÕES -----");
					break;
				}
				System.out.println("----- MENU INICIAL -----\n1 - Listar usuários\n2 - Mudar permissões\n0 - Sair");
				op =  Integer.parseInt(sc.nextLine());
			}
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		
		sc.close();
		
	}

}

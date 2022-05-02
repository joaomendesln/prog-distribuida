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
	
		
		Scanner sc = new Scanner(System.in);
		int op = -1;
		
		try{
			while (op != 0) {
				switch(op) {
				case 1:
					clearScreen();
					System.out.println("----- LISTAR USUÁRIOS -----\n");
					ArrayList<User> users = server.listUsers();

					if (users.size() == 0) System.out.println("Ainda não há usuários cadastrados.\n");
					else {
						int userLoginLength = greatestUserLoginLength(users);
						int userNameLength = greatestUserNameLength(users);
						
						System.out.println(stringHyphen(userLoginLength + userNameLength + 19));
						System.out.println(AllUsersHeader(userLoginLength, userNameLength));
						System.out.println(stringHyphen(userLoginLength + userNameLength + 19));
						
						for(User u : users) {
							System.out.println(AllUsersRow(userLoginLength, userNameLength, u));
						}
	
						System.out.println(stringHyphen(userLoginLength + userNameLength + 19));
						System.out.println();
					}
					
					break;
				case 2:
					clearScreen();
					System.out.println("----- ALTERAR PERMISSÃO DE USUÁRIO -----\n");
					if (server.listUsers().size() == 0) System.out.println("Ainda não há usuários cadastrados.\n");
					else {
						Boolean validPermissionChanging = false;
						while(!validPermissionChanging) {
							System.out.print("Digite o login do usuário o qual deseja alterar permissão: ");
							String loginPermission = sc.nextLine();
							System.out.println();
							int newPermission = 0;
							
							// login não existe
							if (server.findUserByLogin(loginPermission) == null) {
								System.out.println("ERRO: Não existe usuário com esse login\n");
							}
							else {
								System.out.print("Qual permissão deseja dar a " + loginPermission + "?");
								System.out.println("\n0 - Nenhuma\n1 - Apenas leitura\n2 - Leitura e escrita");
								newPermission = Integer.parseInt(sc.nextLine());
								
								// opção inválida
								if (newPermission < 0 || newPermission > 2) 
									System.out.println("ERRO: Opção inválida\n");
							}
							
							
							// deseja tentar mudar a permissão novamente?
							if (server.findUserByLogin(loginPermission) == null || newPermission < 0 || newPermission > 2) {
								System.out.println("Deseja tentar alterar a permissão de um usuário novamente?"
										+ "\n0 - Não"
										+ "\n1 - Sim");
								int opPermissionChanging = Integer.parseInt(sc.nextLine());
								if (opPermissionChanging == 0) { validPermissionChanging = true; clearScreen(); }
								if (opPermissionChanging == 1) { validPermissionChanging = false;
								System.out.println(); } 
							}
							else {
								clearScreen();
								server.updateUserPermission(server.findUserByLogin(loginPermission), newPermission);
								System.out.println("Permissão alterada com sucesso!\n");
								validPermissionChanging = true;
							}
						}
					}
					break;
				}
				System.out.println("----- MENU INICIAL -----\n1 - Listar usuários\n2 - Alterar permissão");
				op = Integer.parseInt(sc.nextLine());
			}
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		
		sc.close();
		
	}
	
	public static void clearScreen() {
        System.out.print("\033[H\033[2J");
	}
	
	public static String stringBlank(int length) {
		String result = "";
		for (int i = 0; i < length; i++)
			result += " ";
		return result;
	}
	
	public static String stringHyphen(int length) {
		String result = "";
		for (int i = 0; i < length; i++)
			result += "-";
		return result;
	}
	
	public static int greatestUserLoginLength(ArrayList<User> users) {
		int result = 5;
		for (User u : users)
			if (u.getLogin().length() > result) result = u.getLogin().length();
		return result;
	}
	
	public static int greatestUserNameLength(ArrayList<User> users) {
		int result = 4;
		for (User u : users)
			if (u.getName().length() > result) result = u.getName().length();
		return result;
	}
	
	public static String AllUsersHeader(int userLoginLength, int userNameLength) {
		String result = "";
		if ((userLoginLength - 5) % 2 == 0) result += "|" + stringBlank((userLoginLength - 5) / 2) + " Login " + stringBlank((userLoginLength - 5) / 2) + "|";
		else result += "|" + stringBlank(((userLoginLength - 5) / 2) + 1) + " Login " + stringBlank((userLoginLength - 5) / 2) + "|";

		if ((userNameLength - 4) % 2 == 0) result += stringBlank((userNameLength - 4) / 2) + " Nome " + stringBlank((userNameLength - 4) / 2) + "|";
		else result += stringBlank(((userNameLength - 4) / 2) + 1) + " Nome " + stringBlank((userNameLength - 4) / 2) + "|";
		
		result += " Permissão |";
		
		return result;
		
	}
	
	public static String AllUsersRow(int userLoginLength, int userNameLength, User user) {
		String result = "";
		if ((userLoginLength - user.getLogin().length()) % 2 == 0) result += "|" + stringBlank((userLoginLength -  user.getLogin().length()) / 2) + " " + user.getLogin() + " " + stringBlank((userLoginLength -  user.getLogin().length()) / 2) + "|";
		else result += "|" + stringBlank(((userLoginLength -  user.getLogin().length()) / 2) + 1) + " " + user.getLogin() + " " + stringBlank((userLoginLength -  user.getLogin().length()) / 2) + "|";

		if ((userNameLength - user.getName().length()) % 2 == 0) result += stringBlank((userNameLength - user.getName().length()) / 2) + " " + user.getName() + " " + stringBlank((userNameLength - user.getName().length()) / 2) + "|";
		else result += stringBlank(((userNameLength - user.getName().length()) / 2) + 1) + " " + user.getName() + " " + stringBlank((userNameLength - user.getName().length()) / 2) + "|";
		
		result += "     " + user.getPermission() + "     |";
		
		
		return result;
	}

}

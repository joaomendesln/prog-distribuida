package com.authentication.rmi;


import java.io.Console;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import com.authentication.model.Good;
import com.authentication.model.User;

public class Main {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

		AuthServerInterface server = (AuthServerInterface)Naming.lookup("rmi://127.0.0.1:1099/AuthServer");
		
		Scanner sc = new Scanner(System.in);

		Console console = System.console();
		int op = -1;
		
		try{
			while (op != 0) {
				switch(op) {
				case 1:
					clearScreen();
					System.out.println("----- CADASTRO -----\n");
					Boolean registred = false;
					while(!registred) {
						System.out.print("Digite seu nome: ");
						String nameRegistration = sc.nextLine();
						System.out.print("Digite seu login: ");
						String loginRegistration = sc.nextLine();
						char[] passwordRegistration = System.console().readPassword("Digite sua senha: ");
						char[] passwordCheckRegistration = System.console().readPassword("Confirme sua senha: ");
						System.out.println();
						
						User user = new User(loginRegistration, nameRegistration, String.valueOf(passwordRegistration), 0);
						
						// login já existe
						if (server.findUserByLogin(loginRegistration) != null) {
							System.out.println("ERRO: Já existe um usuário com esse login\n");
						}
						// senha não confere
						else if (!String.valueOf(passwordRegistration).equals(String.valueOf(passwordCheckRegistration))) {
							System.out.println("ERRO: Senhas não conferem\n");
						}
						
						// deseja tentar se cadastrar novamente?
						if (server.findUserByLogin(loginRegistration) != null || !String.valueOf(passwordRegistration).equals(String.valueOf(passwordCheckRegistration))) {
							System.out.println("Deseja tentar se cadastrar novamente?"
									+ "\n0 - Não"
									+ "\n1 - Sim");
							int opRegistration = Integer.parseInt(sc.nextLine());
							if (opRegistration == 0) { registred = true; 
							clearScreen(); }
							if (opRegistration == 1)  { registred = false; 
							System.out.println(); }
						}
						else {
							clearScreen();
							server.insertUser(user);
							System.out.println("Cadastro realizado com sucesso!\n");
							registred = true;
						}
					}
					
					break;
				case 2:
					clearScreen();
					System.out.println("----- LOGIN -----\n");
					Boolean logged = false, exit = false;
					while (!logged && !exit) {
						System.out.print("Digite seu login: ");
						String loginLogin = sc.nextLine();
						char[] passwordLogin = System.console().readPassword("Digite sua senha: ");
						// tentativa de logar;
						System.out.println();
						User user = new User(loginLogin, String.valueOf(passwordLogin));

						
						// login não existe
						if (server.findUserByLogin(loginLogin) == null) {
							System.out.println("ERRO: Esse usuário não existe\n");
						}
						// senha incorreta
						else if (!server.findUserByLogin(loginLogin).getPassword().equals(String.valueOf(passwordLogin))) {
							System.out.println("ERRO: Senha incorreta\n");
						}
						
						if (server.findUserByLogin(loginLogin) == null || !server.findUserByLogin(loginLogin).getPassword().equals(String.valueOf(passwordLogin))) {
							System.out.println("Deseja tentar logar novamente?"
									+ "\n0 - Não"
									+ "\n1 - Sim");
							int opRegistration = Integer.parseInt(sc.nextLine());
							if (opRegistration == 0)  { logged = true; 
							clearScreen(); }
							if (opRegistration == 1)  { logged = false; 
							System.out.println(); }
						}
						// logou com sucesso
						else {
							clearScreen();
							User userLogged = server.findUserByLogin(loginLogin);
							System.out.println("Olá, " + userLogged.getName() + "!\n");
							
							int opLogged = -1;
							try {
								while (opLogged != 0) {
									switch(opLogged) {
									case 1:
										if (userLogged.getPermission() != 0) {
											clearScreen();
											System.out.println("----- TODOS OS BENS -----\n");
											ArrayList<Good> goods = server.listGoods();
											if (goods.size() == 0) {
												System.out.println("Ainda não há bens cadastrados.\n");
											}
											else {
												int goodNameLength = greatestGoodNameLength(goods);
												int goodOwnerNameLength = greatestGoodOwnerNameLength(goods);
												
												System.out.println(stringHyphen(goodNameLength + goodOwnerNameLength + 7));
												System.out.println(AllGoodsHeader(goodNameLength, goodOwnerNameLength));
												System.out.println(stringHyphen(goodNameLength + goodOwnerNameLength + 7));
												
												for(Good g : goods) {
													System.out.println(AllGoodsRow(goodNameLength, goodOwnerNameLength, g));
												}
												
												System.out.println(stringHyphen(goodNameLength + goodOwnerNameLength + 7));
												System.out.println();
											}
										}
										break;
									case 2:
										if (userLogged.getPermission() == 2) {
											clearScreen();
											System.out.println("----- MEUS BENS -----\n");
											ArrayList<Good> goodsByOwner = server.listGoodsByOwner(userLogged);
											if (goodsByOwner.size() == 0) {
												System.out.println("Você ainda não cadastrou um bem.\n");
											}
											else {
												int goodNameLength = greatestGoodNameLength(goodsByOwner);
												
												System.out.println(stringHyphen(goodNameLength + 4));
												System.out.println(GoodsByOwnerHeader(goodNameLength));
												System.out.println(stringHyphen(goodNameLength + 4));
												
												for(Good g : goodsByOwner) {
													System.out.println(GoodsByOwnerRow(goodNameLength, g));
												}
												
												System.out.println(stringHyphen(goodNameLength + 4));
												System.out.println();
											}
										}
										break;
									case 3:
										if (userLogged.getPermission() == 2) {
											clearScreen();
											System.out.println("----- INSERÇÃO DE BEM -----\n");
											System.out.print("Digite o nome do bem a ser inserido: ");
											String goodNameInsertion = sc.nextLine();

											clearScreen();
											server.insertGood(new Good(goodNameInsertion, userLogged));
											System.out.println("Bem inserido com sucesso!\n");
										}
										break;
									}
									
									if (userLogged.getPermission() == 0)
										System.out.println("Você não possui permissão para listar os bens cadastrados.\n");
									
									System.out.println("----- MENU -----");
									if (userLogged.getPermission() != 0) System.out.println("1 - Listar todos os bens");
									if (userLogged.getPermission() == 2) System.out.println("2 - Listar meus bens\n3 - Inserir bem");
									System.out.println("0 - Sair");
									opLogged = Integer.parseInt(sc.nextLine());
									if (opLogged == 0) {
										exit = true;
										clearScreen();
									}
									
								}
							}
							catch (NumberFormatException ex){
					            ex.printStackTrace();
					        }
	
						}
					}
					break;
				}
				System.out.println("----- MENU INICIAL -----\n1 - Cadastro\n2 - Login\n0 - Sair");
				op =  Integer.parseInt(sc.nextLine());
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
	
	public static int greatestGoodNameLength(ArrayList<Good> goods) {
		int result = 3;
		for (Good g : goods)
			if (g.getName().length() > result) result = g.getName().length();
		return result;
	}
	
	public static int greatestGoodOwnerNameLength(ArrayList<Good> goods) {
		int result = 4;
		for (Good g : goods)
			if (g.getOwner().getName().length() > result) result = g.getOwner().getName().length();
		return result;
	}
	
	public static String AllGoodsHeader(int goodNameLength, int goodOwnerNameLength) {
		String result = "";
		if ((goodNameLength - 3) % 2 == 0) result += "|" + stringBlank((goodNameLength - 3) / 2) + " Bem " + stringBlank((goodNameLength - 3) / 2) + "|";
		else result += "|" + stringBlank(((goodNameLength - 3) / 2) + 1) + " Bem " + stringBlank((goodNameLength - 3) / 2) + "|";

		if ((goodOwnerNameLength - 4) % 2 == 0) result += stringBlank((goodOwnerNameLength - 4) / 2) + " Dono " + stringBlank((goodOwnerNameLength - 4) / 2) + "|";
		else result += stringBlank(((goodOwnerNameLength - 4) / 2) + 1) + " Dono " + stringBlank((goodOwnerNameLength - 4) / 2) + "|";
		return result;
	}
	
	public static String AllGoodsRow(int goodNameLength, int goodOwnerNameLength, Good good) {
		String result = "";
		if ((goodNameLength - good.getName().length()) % 2 == 0) result += "|" + stringBlank((goodNameLength -  good.getName().length()) / 2) + " " + good.getName() + " " + stringBlank((goodNameLength -  good.getName().length()) / 2) + "|";
		else result += "|" + stringBlank(((goodNameLength -  good.getName().length()) / 2) + 1) + " " + good.getName() + " " + stringBlank((goodNameLength -  good.getName().length()) / 2) + "|";

		if ((goodOwnerNameLength - good.getOwner().getName().length()) % 2 == 0) result += stringBlank((goodOwnerNameLength - good.getOwner().getName().length()) / 2) + " " + good.getOwner().getName() + " " + stringBlank((goodOwnerNameLength - good.getOwner().getName().length()) / 2) + "|";
		else result += stringBlank(((goodOwnerNameLength - good.getOwner().getName().length()) / 2) + 1) + " " + good.getOwner().getName() + " " + stringBlank((goodOwnerNameLength - good.getOwner().getName().length()) / 2) + "|";
		return result;
	}

	
	public static String GoodsByOwnerHeader(int goodNameLength) {
		String result = "";
		if ((goodNameLength - 3) % 2 == 0) result += "|" + stringBlank((goodNameLength - 3) / 2) + " Bem " + stringBlank((goodNameLength - 3) / 2) + "|";
		else result += "|" + stringBlank(((goodNameLength - 3) / 2) + 1) + " Bem " + stringBlank((goodNameLength - 3) / 2) + "|";
		return result;
	}
	
	public static String GoodsByOwnerRow(int goodNameLength, Good good) {
		String result = "";
		if ((goodNameLength - good.getName().length()) % 2 == 0) result += "|" + stringBlank((goodNameLength -  good.getName().length()) / 2) + " " + good.getName() + " " + stringBlank((goodNameLength -  good.getName().length()) / 2) + "|";
		else result += "|" + stringBlank(((goodNameLength -  good.getName().length()) / 2) + 1) + " " + good.getName() + " " + stringBlank((goodNameLength -  good.getName().length()) / 2) + "|";

		return result;
	}

}
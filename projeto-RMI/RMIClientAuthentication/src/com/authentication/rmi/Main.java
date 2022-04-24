package com.authentication.rmi;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import com.authentication.model.User;

public class Main {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {

		AuthServerInterface server = (AuthServerInterface)Naming.lookup("rmi://127.0.0.1:1099/AuthServer");
		
		Scanner sc = new Scanner(System.in);
		int op = -1;
		
		try{
			while (op != 0) {
				switch(op) {
				case 1:
					System.out.println("----- TELA DE CADASTRO -----");
					Boolean registred = false;
					while(!registred) {
						System.out.print("Digite seu nome: ");
						String nameRegistration = sc.nextLine();
						System.out.print("Digite seu login: ");
						String loginRegistration = sc.nextLine();
						System.out.print("Digite sua senha: ");
						String passwordRegistration = sc.nextLine(); // modo de senha
						System.out.print("Confirme sua senha: ");
						String passwordCheckRegistration = sc.nextLine(); // modo de senha
						
						User user = new User(loginRegistration, nameRegistration, passwordRegistration);
						
						// login já existe
						if (server.findByLogin(loginRegistration) != null) {
							System.out.println("ERRO: Já existe um usuário com esse login");
						}
						// senha não confere
						else if (!passwordRegistration.equals(passwordCheckRegistration)) {
							System.out.println("ERRO: Senhas não conferem");
						}
						
						// deseja tentar cadastrar novamente?
						if (server.findByLogin(loginRegistration) != null || !passwordRegistration.equals(passwordCheckRegistration)) {
							System.out.println("Deseja tentar se cadastrar novamente?"
									+ "\n0 - Não"
									+ "\n1 - Sim");
							int opRegistration = Integer.parseInt(sc.nextLine());
							if (opRegistration == 0) registred = true;
							if (opRegistration == 1) registred = false;
						}
						else {
							System.out.println("Cadastro realizado com sucesso!\n");
							server.register(user);
							registred = true;
						}
					}
					
					break;
				case 2:
					System.out.println("----- TELA LOGIN -----");
					Boolean logged = false;
					while (!logged) {
						System.out.print("Digite seu login: ");
						String loginLogin = sc.nextLine();
						System.out.print("Digite sua senha: ");
//						char[] password = System.console().readPassword("digite sua senha: ");
						String passwordLogin = sc.nextLine(); // modo de senha2
						// tentativa de logar;
						User user = new User(loginLogin, passwordLogin);

						
						// login não existe
						if (server.findByLogin(loginLogin) == null) {
							System.out.println("ERRO: Esse usuário não existe");
						}
						// senha incorreta
						else if (!server.findByLogin(loginLogin).getPassword().equals(passwordLogin)) {
							System.out.println("ERRO: Senha incorreta");
						}
						
						if (server.findByLogin(loginLogin) == null || !server.findByLogin(loginLogin).getPassword().equals(passwordLogin)) {
							System.out.println("Deseja tentar logar novamente?"
									+ "\n0 - Não"
									+ "\n1 - Sim");
							int opRegistration = Integer.parseInt(sc.nextLine());
							if (opRegistration == 0) logged = true;
							if (opRegistration == 1) logged = false;
						}
						else {
							User userLogged = server.findByLogin(loginLogin);
							System.out.println("Olá, " + userLogged.getName() + "!");
							
							// Listar
							// Cadastrar
							// Sair
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

}
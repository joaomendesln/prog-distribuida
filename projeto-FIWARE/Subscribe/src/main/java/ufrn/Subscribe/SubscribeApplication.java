package ufrn.Subscribe;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SubscribeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscribeApplication.class, args);
		
        String ip = "", port = "";
        try {
            ip = InetAddress.getLocalHost().toString().split("/")[1];
 
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        port = args[0].split("=")[1];

		Scanner scanner = new Scanner(System.in);
		Integer favoriteStyle = 
				new SubscribeUtil().getSubscriptionTypeByIpAndPort(ip, port);
		
        System.out.print("\033[H\033[2J"); // clear screen
        
		if (favoriteStyle == -1) {
			System.out.println("Você ainda não tem um estilo favorito de roupas cadastrado");
			System.out.println("Qual o seu estilo de roupas favorito?");System.out.println("1 - Esportivo\n2 - Tradicional\n3 - Para festas\n0 - Voltar");
			favoriteStyle = Integer.parseInt(scanner.nextLine());
			if (favoriteStyle != 0) {
				new SubscribeUtil().subscribe(ip, port, favoriteStyle);
				String favoriteStyleString = "";
				switch (favoriteStyle) {
				case 1: favoriteStyleString = "Esportivo"; break;
				case 2: favoriteStyleString = "Tradicional"; break;
				case 3: favoriteStyleString = "Para festas"; break;
				}
				System.out.println("Cadastro realizado com sucesso!");
				System.out.println("Abaixo, a listagem das roupas do estilo " + favoriteStyleString + " disponíveis.");
				System.out.println("Sempre que chegarem roupas do estilo " + favoriteStyleString + " ela será adicionada à lista.");
			}
		}
		else {
			System.out.print("O seu estilo favorito de roupas é ");
			switch (favoriteStyle) {
			case 1: System.out.println("Esportivo"); break;
			case 2: System.out.println("Tradicional"); break;
			case 3: System.out.println("Para festas"); break;
			}
		}
		
		scanner.close();
	}

}

package com.authentication.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PermissionManagement {
    public static void generatePermission() throws FileNotFoundException {
        Random gerador = new Random();
        List<String> logins = new ArrayList<>();
        int cont = 0;
        while(cont < 100) {
            String login = "";
            for (int i = 0; i < 8; i++) {
                login += String.valueOf(gerador.nextInt(9));
            }
            if (!logins.contains(login)) {
                logins.add(login);
                cont += 1;
            }
        }

        String result = logins.get(0);
        for (int i = 1; i < 100; i++) result += "," + logins.get(i);

        try (PrintWriter out = new PrintWriter("clientWithSavingPermission.txt")) {
            out.println(result);
        }

        cont = 0;
        while(cont < 200) {
            String login = "";
            for (int i = 0; i < 8; i++) {
                login += String.valueOf(gerador.nextInt(9));
            }
            if (!logins.contains(login)) {
                logins.add(login);
                cont += 1;
            }
        }
        for (int i = 0; i < 200; i++) result += "," + logins.get(i + 100);


        try (PrintWriter out = new PrintWriter("clientWithListingPermission.txt")) {
            out.println(result);
        }

    }
}

package com.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class AuthenticationApplication {



	public static void main(String[] args) throws FileNotFoundException {

//		try {
//
//			// Two absolute paths
//
//			File absolutePath1 = new File("/Users/joaomendes_ln/Documents/UFRN Temporário/9º período/ProgDistribuida/demo/src/main/java/com/authentication/util");
//			System.out.println("Absolute Path1: " + absolutePath1);
//			File absolutePath2 = new File("/Users/joaomendes_ln/Documents/UFRN Temporário/9º período/ProgDistribuida/demo/src/main/java/com/authentication/controller");
//			System.out.println("Absolute Path2: " + absolutePath2);
//
//			// convert the absolute path to URI
//			URI path1 = absolutePath1.toURI();
//			URI path2 = absolutePath2.toURI();
//
//			// create a relative path from the two paths
//			URI relativePath = path1.relativize(path2);
//
//			// convert the URI to string
//			String path = relativePath.getPath();
//
//			System.out.println("Relative Path: " + path);
//
//
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
		SpringApplication.run(AuthenticationApplication.class, args);
	}
}

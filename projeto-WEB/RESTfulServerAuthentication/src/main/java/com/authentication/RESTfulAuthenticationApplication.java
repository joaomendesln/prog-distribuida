package com.authentication;

import com.authentication.repository.ClientRepository;
import com.authentication.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RESTfulAuthenticationApplication {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private GoodRepository goodRepository;

    public static void main(String[] args) {
        SpringApplication.run(RESTfulAuthenticationApplication.class, args);
    }

}

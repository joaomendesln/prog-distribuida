package com.authentication;

import com.authentication.model.Client;
import com.authentication.model.Good;
import com.authentication.repository.ClientRepository;
import com.authentication.repository.GoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ClientRepository clientRepository, GoodRepository goodRepository) {

        return args -> {
            Client client1 = new Client("50253876", "João", "12345", "ROLE_USER", 2);
            Client client2 = new Client("10101010", "Gabriel", "12345","ROLE_USER", 0);

            clientRepository.save(client1);
            clientRepository.save(client2);

            clientRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));


            goodRepository.save(new Good("Computador", client1));
            goodRepository.save(new Good("Celular", client1));

            goodRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });

        };
    }
}
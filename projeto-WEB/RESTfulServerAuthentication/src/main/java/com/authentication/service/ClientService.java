package com.authentication.service;

import com.authentication.exception.ClientNotFoundException;
import com.authentication.model.Client;
import com.authentication.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    ClientRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> optional = repository.findClientByUsername(username);

        if(optional.isPresent()) {
            return new org.springframework.security.core.userdetails.User(optional.get().getUsername(), encoder().encode(optional.get().getPassword()), Collections.singletonList(new SimpleGrantedAuthority(optional.get().getRole())));
//            return new org.springframework.security.core.userdetails.User(optional.get().getUsername(), encoder().encode(optional.get().getPassword()), null);
        }

        throw new ClientNotFoundException(optional.get().getId());
    }

    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

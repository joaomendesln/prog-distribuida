package com.authentication.controller;

import com.authentication.dto.LoginDTO;
import com.authentication.dto.TokenDTO;
import com.authentication.repository.ClientRepository;
import com.authentication.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private ClientRepository clientRepository;


    @PostMapping
    public ResponseEntity<TokenDTO> auth(@RequestBody @Validated LoginDTO loginDTO){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = tokenService.generateToken(authentication);

        loginDTO.setId(clientRepository.findClientByUsername(loginDTO.getUsername()).get().getId());
        loginDTO.setPersonalname(clientRepository.findClientByUsername(loginDTO.getUsername()).get().getPersonalname());
        loginDTO.setPermission(clientRepository.findClientByUsername(loginDTO.getUsername()).get().getPermission());
        loginDTO.setRole(clientRepository.findClientByUsername(loginDTO.getUsername()).get().getRole());


        return ResponseEntity.ok(new TokenDTO("Bearer", token, loginDTO));
    }

}
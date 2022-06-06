package com.authentication.dto;

import lombok.Data;

@Data
public class LoginDTO {
    Long id;
    String username;
    private String personalname;
    String password;
    private String role;
    private int permission;
}

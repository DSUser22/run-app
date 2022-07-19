package com.dasha.usersystem.pattern;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

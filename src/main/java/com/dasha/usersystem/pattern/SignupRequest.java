package com.dasha.usersystem.pattern;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private Set<String> roles;
}

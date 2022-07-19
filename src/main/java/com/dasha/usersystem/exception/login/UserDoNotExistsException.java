package com.dasha.usersystem.exception.login;

import org.springframework.security.core.AuthenticationException;

public class UserDoNotExistsException extends AuthenticationException {
    public UserDoNotExistsException(String msg) {
        super(msg);
    }
}

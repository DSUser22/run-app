package com.dasha.usersystem.exception.registration;
import org.springframework.security.authentication.AccountStatusException;

public class UserNotEnabledException extends AccountStatusException {

    public UserNotEnabledException(String msg) {
        super(msg);
    }
}

package com.dasha.usersystem.exception.registration;

public class RoleDoNotExistException extends RuntimeException{
    public RoleDoNotExistException(String message) {
        super(message);
    }
}

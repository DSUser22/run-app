package com.dasha.usersystem.exception;

import com.dasha.usersystem.exception.login.UserDoNotExistsException;
import com.dasha.usersystem.exception.plan.PlanException;
import com.dasha.usersystem.exception.registration.ConfirmationException;
import com.dasha.usersystem.exception.login.TokenRefreshException;
import com.dasha.usersystem.exception.registration.RoleDoNotExistException;
import com.dasha.usersystem.exception.registration.SendingMessageException;
import com.dasha.usersystem.exception.registration.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = {
            UserDoNotExistsException.class,
            RoleDoNotExistException.class,
            TokenRefreshException.class,
            ConfirmationException.class,
            BadCredentialsException.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<Object> handleUnauthorizedException(RuntimeException e){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ExceptionMessage exception = new ExceptionMessage(status, ZonedDateTime.now(), e.getMessage());
        return new ResponseEntity<>(exception, status);
    }
    @ExceptionHandler(value = {
            UserAlreadyExistsException.class,
            PlanException.class}
    )
    public ResponseEntity<Object> handleConflictException(RuntimeException e){
        HttpStatus status = HttpStatus.CONFLICT;
        ExceptionMessage exception = new ExceptionMessage(status, ZonedDateTime.now(), e.getMessage());
        return new ResponseEntity<>(exception, status);
    }
    @ExceptionHandler(value = {SendingMessageException.class})
    public ResponseEntity<Object> handleInternalServerErrorException(SendingMessageException e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionMessage exception = new ExceptionMessage(status, ZonedDateTime.now(), e.getMessage());
        return new ResponseEntity<>(exception, status);
    }
}

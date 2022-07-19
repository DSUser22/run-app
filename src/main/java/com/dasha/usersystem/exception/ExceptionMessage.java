package com.dasha.usersystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ExceptionMessage {
    private final HttpStatus httpStatus;
    private final ZonedDateTime zonedDateTime;
    private final String message;
}

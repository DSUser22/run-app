package com.dasha.usersystem.security.auth;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    @Override
    public boolean test(String s) {
        // TODO: businessLogic
        return Pattern.compile(EMAIL_PATTERN).matcher(s).matches();
    }
}


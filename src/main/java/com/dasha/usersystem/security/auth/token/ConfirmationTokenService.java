package com.dasha.usersystem.security.auth.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;

    public void saveConfirmationToken(ConfirmationToken token){
        repository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return repository.findByToken(token);
    }

    public int setConfirmedAt(String token){
        return repository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public void delete(long userId){
        repository.deleteAllByAppUserId(userId);
    }

}

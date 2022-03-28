package com.dasha.usersystem.appuser;

import com.dasha.usersystem.security.auth.token.ConfirmationToken;
import com.dasha.usersystem.security.auth.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "user with email %s not found";
    private final AppUserRepo appUserRepo;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        AppUser user =  appUserRepo.findByUsername(email)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
        if(!user.getEnabled()){
            throw new IllegalStateException("user is not enabled");
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
    public void isUserExists(String email){
        if(appUserRepo.findByUsername(email).isPresent()){
            throw new IllegalStateException("user already exists");
        }
    }
    public String signUpUser(AppUser appUser){
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepo.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableAppUser(String email){
        return appUserRepo.enableAppUser(email);
    }

    public void deleteAppUser(String username){
        appUserRepo.deleteAppUserByUsername(username);
    }
}

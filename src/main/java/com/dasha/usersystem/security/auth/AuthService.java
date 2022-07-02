package com.dasha.usersystem.security.auth;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final AppUserRepo appUserRepo;

    private final static String USER_NOT_FOUND_MESSAGE = "user with email %s not found";

    public UserDetails auth(String username){
        return loadUserByUsername(username);
    }
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        AppUser user = appUserRepo.findByUsername(email)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
        if(!user.getEnabled()){
            throw new IllegalStateException("user is not enabled");
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
    public Long findIdByUsername(String username){
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));
        return user.getId();
    }


    public void deleteAppUser(Long userId) {
        appUserRepo.deleteById(userId);
    }
}

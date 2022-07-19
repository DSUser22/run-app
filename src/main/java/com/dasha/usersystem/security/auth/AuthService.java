package com.dasha.usersystem.security.auth;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRepo;
import com.dasha.usersystem.exception.registration.UserNotEnabledException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final AppUserRepo appUserRepo;

    public UserDetails auth(String username){
        return loadUserByUsername(username);
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found"));
        if(!user.getEnabled()){
            throw new UserNotEnabledException("User not enabled");
        }
        return user;
    }
    public Long findIdByUsername(String username){
        AppUser user = appUserRepo.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found"));
        return user.getId();
    }

    public void deleteAppUser(Long userId) {
        appUserRepo.deleteById(userId);
    }
}

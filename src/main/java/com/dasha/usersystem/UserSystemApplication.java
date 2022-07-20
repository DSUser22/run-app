package com.dasha.usersystem;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRepo;
import com.dasha.usersystem.appuser.role.ERole;
import com.dasha.usersystem.appuser.role.Role;
import com.dasha.usersystem.appuser.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class UserSystemApplication implements CommandLineRunner {
    @Autowired private RoleRepository roleRepo;
    @Autowired private AppUserRepo appUserRepo;
    @Autowired private PasswordEncoder encoder;


    public static void main(String[] args) {
        SpringApplication.run(UserSystemApplication.class, args);
    }

    @Override
    public void run(String... args) {
        roleRepo.save(new Role(ERole.USER));
        roleRepo.save(new Role(ERole.ADMIN));

        /*AppUser appUser = new AppUser("dashaasavel2001@gmail.com", encoder.encode("dasha"));
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName(ERole.USER).orElseThrow(IllegalStateException::new));
        roles.add(roleRepo.findByName(ERole.ADMIN).orElseThrow(IllegalStateException::new));
        appUser.setEnabled(true);
        appUser.setAuthorities(roles);
        appUserRepo.save(appUser);*/
    }

}

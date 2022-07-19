package com.dasha.usersystem.security.registration;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRepo;
import com.dasha.usersystem.appuser.role.ERole;
import com.dasha.usersystem.appuser.role.Role;
import com.dasha.usersystem.appuser.role.RoleRepository;
import com.dasha.usersystem.exception.registration.ConfirmationException;
import com.dasha.usersystem.exception.registration.RoleDoNotExistException;
import com.dasha.usersystem.exception.registration.UserAlreadyExistsException;
import com.dasha.usersystem.pattern.SignupRequest;
import com.dasha.usersystem.security.auth.confToken.ConfirmationToken;
import com.dasha.usersystem.security.auth.confToken.ConfirmationTokenService;
import com.dasha.usersystem.security.email.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {
    private final AppUserRepo appUserRepo;
    private final RoleRepository roleRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private PasswordEncoder encoder;
    @Transactional
    public ResponseEntity<?> register(SignupRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        if(appUserRepo.findByUsername(username).isPresent()){
            AppUser appUser = appUserRepo.findByUsername(username).get();
            Long userId = appUser.getId();
            boolean isEnabled = appUser.getEnabled();
            if(isEnabled) {
                throw new UserAlreadyExistsException("user already exists");
            } else if(!isEnabled && confirmationTokenService.findLastConfTokenByAppUserId(userId)
                    .getExpiresAt().compareTo(LocalDateTime.now()) > 0) {
                throw new UserAlreadyExistsException("need to confirm");
            } else {
                appUserRepo.deleteById(userId);
            }
        }
        AppUser user = new AppUser(username,
                encoder.encode(password));

        Set<String> strRoles = request.getRoles();
        Collection<Role> roles = new ArrayList<>();
        if(strRoles!=null && strRoles.contains("admin")){
            Role adminRole = roleRepository.findByName(ERole.ADMIN)
                    .orElseThrow(() -> new RoleDoNotExistException("role wasn't found"));
            roles.add(adminRole);
        }
        Role userRole = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new RoleDoNotExistException("role wasn't found"));
        roles.add(userRole);
        /*
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if(role.equals("admin")){
                    Role adminRole = roleRepository.findByName(ERole.ADMIN)
                            .orElseThrow(() -> new RoleDoNotExistsException("role wasn't found"));
                    roles.add(adminRole);
                }
                Role userRole = roleRepository.findByName(ERole.USER)
                        .orElseThrow(() -> new RoleDoNotExistsException("role wasn't found"));
                roles.add(userRole);
            });
        }*/
        user.setAuthorities(roles);

        // проверка на последний токен пользователя

        sendConfToken(user);

        appUserRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build(); // need to confirm
    }
    public void sendConfToken(AppUser appUser){

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1), // 15
                appUser
        );
        String link = "http://localhost:8080/api/v1/auth/confirm?token="+token;
        emailService.sendToConfirm(
                appUser.getUsername(),
                link
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }

    public ResponseEntity<?> confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(()->new ConfirmationException("token not found"));

        if(confirmationToken.getConfirmedAt()!=null){
            throw new ConfirmationException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            Long userId = confirmationToken.getAppUser().getId();
            appUserRepo.deleteById(userId);
            throw new ConfirmationException("Time is over. Need to sign up again");
        }

        confirmationTokenService.setConfirmedAt(token);
        String email = confirmationToken.getAppUser().getUsername();

        appUserRepo.enableAppUser(confirmationToken.getAppUser().getUsername());
        emailService.sendWhenConfirmed(email);
        return ResponseEntity.ok().build();
    }
}

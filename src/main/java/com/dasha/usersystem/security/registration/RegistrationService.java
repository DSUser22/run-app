package com.dasha.usersystem.security.registration;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRepo;
import com.dasha.usersystem.appuser.AppUserRole;
import com.dasha.usersystem.security.auth.AuthRequest;
import com.dasha.usersystem.security.auth.token.ConfirmationToken;
import com.dasha.usersystem.security.auth.token.ConfirmationTokenService;
import com.dasha.usersystem.security.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserRepo appUserRepo;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    public String register(AuthRequest request) {
        if(appUserRepo.findByUsername(request.getUsername()).isPresent()){
            throw new IllegalStateException("user is already exists");
        }

        String token = signUpUser(
                new AppUser(
                        request.getUsername(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );

        String link = "http://localhost:8080/api/v1/confirm?token="+token;
        emailService.sendToConfirm(
                request.getUsername(),
                link
        );

        return "check your mail for a confirmation letter";
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
    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(()->new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt()!=null){
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("time is over");
        }

        confirmationTokenService.setConfirmedAt(token);  // задаём время подтверждения
        String email = confirmationToken.getAppUser().getUsername();

        appUserRepo.enableAppUser(confirmationToken.getAppUser().getUsername()); // делаем user'а доступным
        emailService.sendWhenConfirmed(email);

        return "confirmed";
    }
}

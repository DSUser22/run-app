package com.dasha.usersystem.security.auth;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appUserInfo.AppUserInfo;
import com.dasha.usersystem.appuser.AppUserRole;
import com.dasha.usersystem.appuser.AppUserService;
import com.dasha.usersystem.security.email.EmailService;
import com.dasha.usersystem.appUserInfo.AppUserInfoService;
import com.dasha.usersystem.security.auth.token.ConfirmationToken;
import com.dasha.usersystem.security.auth.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthService {

    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final AppUserInfoService appUserInfoService;

    public UserDetails auth(String username){
        return appUserService.loadUserByUsername(username);
    }

    public String register(AuthRequest request) {
        appUserService.isUserExists(request.getUsername());
        String token = appUserService.signUpUser(
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

        appUserService.enableAppUser(confirmationToken.getAppUser().getUsername()); // делаем user'а доступным
        emailService.sendWhenConfirmed(email);

        // создаем AppUserInfo
        AppUserInfo appUserInfo = new AppUserInfo(confirmationToken.getAppUser());
        appUserInfoService.addUserInfo(appUserInfo);

        return "confirmed, thanks for registration";
    }
}

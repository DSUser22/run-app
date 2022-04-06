package com.dasha.usersystem.appuser;

import com.dasha.usersystem.security.auth.token.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {
    private AppUserService underTest;
    AppUser appUser;
    String username;

    @Mock private AppUserRepo appUserRepo;
    @Mock private ConfirmationTokenService confirmationTokenService;
    @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        underTest = new AppUserService(appUserRepo, confirmationTokenService, bCryptPasswordEncoder);
        appUser = new AppUser("dasha", "dasha", AppUserRole.USER);
        username = appUser.getUsername();
    }

    @Test
    void isUserExists() {
        underTest.isUserExists(username);
        verify(appUserRepo).findByUsername(username);
    }

    @Test
    void signUpUser() {
        underTest.signUpUser(appUser);
        ArgumentCaptor<AppUser> argumentCaptor = ArgumentCaptor.forClass(AppUser.class);

        verify(appUserRepo).save(argumentCaptor.capture());
        AppUser capturedAppUser = argumentCaptor.getValue();

        assertThat(capturedAppUser).isEqualTo(appUser);
    }

    @Test
    void enableAppUser() {
        underTest.enableAppUser(username);
        verify(appUserRepo).enableAppUser(username);
    }

    @Test
    void deleteAppUser() {
        underTest.deleteAppUser(username);
        verify(appUserRepo).deleteAppUserByUsername(username);
    }
}
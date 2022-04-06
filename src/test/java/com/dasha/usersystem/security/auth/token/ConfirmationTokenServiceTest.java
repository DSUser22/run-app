package com.dasha.usersystem.security.auth.token;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {
    @Mock private ConfirmationTokenRepository tokenRepo;
    private ConfirmationTokenService underTest;

    ConfirmationToken token;
    String stringToken;

    @BeforeEach
    void setUp() {
        underTest = new ConfirmationTokenService(tokenRepo);

        AppUser appUser = new AppUser("dasha_savel2001@mail.ru", "dasha", AppUserRole.USER);
        stringToken = UUID.randomUUID().toString();
        token = new ConfirmationToken(
                stringToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
    }

    @Test
    void saveConfirmationToken() {
        //when
        underTest.saveConfirmationToken(token);

        //then
        ArgumentCaptor<ConfirmationToken> tokenArgumentCaptor = ArgumentCaptor.forClass(ConfirmationToken.class);
        verify(tokenRepo).save(tokenArgumentCaptor.capture()); // то же, что и ушло в репо

        ConfirmationToken capturedToken = tokenArgumentCaptor.getValue();
        assertThat(capturedToken).isEqualTo(token); // то же хранится, что и было послано

    }

    @Test
    void getToken() {
        underTest.getToken(stringToken);
        verify(tokenRepo).findByToken(stringToken);
    }

    @Test
    void setConfirmedAt() {
        underTest.setConfirmedAt(stringToken);
        verify(tokenRepo).updateConfirmedAt(stringToken, LocalDateTime.now());
    }

    @Test
    void delete() {
        underTest.delete("username");
        verify(tokenRepo).deleteAllByAppUserUsername("username");
    }
}
package com.dasha.usersystem.security.auth.token;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.appuser.AppUserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ConfirmationTokenRepositoryTest {
    @Autowired
    private ConfirmationTokenRepository underTest;
    private ConfirmationToken token;
    private String stringToken;

    @BeforeEach
    void setUp() {
        AppUser appUser = new AppUser("dasha_savel2001@mail.ru", "dasha", AppUserRole.USER);
        stringToken = UUID.randomUUID().toString();
        token = new ConfirmationToken(
                stringToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        underTest.save(token);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByToken() {
        boolean b = underTest.findByToken(token.getToken()).isPresent();
        assertThat(b).isTrue();
    }

    @Test
    void shouldUpdateConfirmedAt() {
        int r = underTest.updateConfirmedAt(stringToken, LocalDateTime.now());
        assertThat(r).isEqualTo(1);
    }

    @Test
    void shouldDeleteAllByAppUserUsername() {
        String username = token.getAppUser().getUsername();
        underTest.deleteAllByAppUserUsername(username);
        boolean b = underTest.findByToken(token.getToken()).isPresent();
        assertThat(b).isFalse();
    }
}
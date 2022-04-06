package com.dasha.usersystem.appuser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppUserRepoTest {
    @Autowired
    private AppUserRepo underTest;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser(
                "dasha_savel2001@mail.ru",
                "dasha",
                AppUserRole.USER);
        underTest.save(appUser);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindByUsername() {
        String username = "dasha_savel2001@mail.ru";
        boolean b = underTest.findByUsername(username).isPresent();

        assertThat(b).isTrue();
    }

    @Test
    void itShouldEnableAppUser() {
        //given
        String username = "dasha_savel2001@mail.ru";

        //when
        underTest.enableAppUser(username);

        //then
        assertThat(underTest
                .findByUsername(username)
                .orElseThrow(()->new IllegalStateException("not found"))
                .getEnabled())
                .isTrue();
    }

    @Test
    void itShouldDeleteAppUserByUsername() {
        //given
        String username = "dasha_savel2001@mail.ru";

        //when
        underTest.deleteAppUserByUsername(username);

        //then
        assertThat(underTest.findByUsername(username).isPresent()).isFalse();
    }
}
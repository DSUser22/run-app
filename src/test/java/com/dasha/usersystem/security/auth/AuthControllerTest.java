package com.dasha.usersystem.security.auth;

import com.dasha.usersystem.appUserInfo.AppUserInfoService;
import com.dasha.usersystem.appuser.AppUserService;
import com.dasha.usersystem.plan.PlanService;
import com.dasha.usersystem.security.auth.token.ConfirmationTokenService;
import com.dasha.usersystem.security.jwt.JWTUtility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthControllerTest {
    /*@Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthController underTest;
    @MockBean private AuthService authService;
    @MockBean private PlanService planService;
    @MockBean private AppUserService appUserService;
    @MockBean private AppUserInfoService appUserInfoService;
    @MockBean private ConfirmationTokenService confirmationTokenService;
    @MockBean private JWTUtility utility;
    @MockBean private AuthenticationManager authManager;


    @BeforeEach
    void setUp() {
        underTest = new AuthController(authService,
                planService, appUserService,
                appUserInfoService,
                confirmationTokenService,
                utility,
                authManager);
    }*/

    @AfterEach
    void tearDown() {

    }

    @Test
    public void test() throws Exception {

    }


    @Test
    void register() {
    }

    @Test
    void auth() {
    }

    @Test
    void confirm() throws Exception {

    }

    @Test
    void delete() {
    }

    @Test
    void getUsername() {
    }
}
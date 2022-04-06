package com.dasha.usersystem;

import com.dasha.usersystem.appUserInfo.AppUserInfoService;
import com.dasha.usersystem.appuser.AppUserService;
import com.dasha.usersystem.plan.PlanService;
import com.dasha.usersystem.security.auth.AuthController;
import com.dasha.usersystem.security.auth.AuthService;
import com.dasha.usersystem.security.auth.token.ConfirmationTokenService;
import com.dasha.usersystem.security.jwt.JWTUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserSystemApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthController underTest;
    @MockBean
    private AuthService authService;
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
    }

    @Test
    void contextLoads() throws Exception{
        this.mockMvc.perform(get("/api/v1/hello"))
                .andExpect(status().isForbidden());
    }

}

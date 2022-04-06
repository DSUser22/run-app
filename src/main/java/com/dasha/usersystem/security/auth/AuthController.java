package com.dasha.usersystem.security.auth;

import com.dasha.usersystem.appUserInfo.AppUserInfoService;
import com.dasha.usersystem.appuser.AppUserService;
import com.dasha.usersystem.plan.PlanService;
import com.dasha.usersystem.security.auth.token.ConfirmationTokenService;
import com.dasha.usersystem.security.jwt.JWTUtility;
import com.dasha.usersystem.security.jwt.JWTResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final PlanService planService;
    private final AppUserService appUserService;
    private final AppUserInfoService appUserInfoService;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    private final JWTUtility jwtUtility;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "register")
    public String register(@Valid @RequestBody AuthRequest request){
        return authService.register(request);
    }

    @PostMapping(path = "auth")
    public JWTResponse auth(@Valid @RequestBody AuthRequest request){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch(AuthenticationException e){
            throw new IllegalStateException(e);
        }
        final UserDetails userDetails = authService.auth(request.getUsername());
        final String token = jwtUtility.generateToken(userDetails);
        return new JWTResponse(token);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){
        return authService.confirmToken(token);
    }

    @DeleteMapping(path = "delete")
    public void delete(@RequestHeader(name="Authorization") String token){
        String username = getUsername(token);

        planService.deletePlan(username);
        confirmationTokenService.delete(username);
        appUserInfoService.deleteAppUserInfo(username);
        appUserService.deleteAppUser(username);
    }
    @GetMapping(path = "hello")
    public String hello(){
        return "hello, guest";
    }

    String getUsername(String token){
        return jwtUtility.getUsernameFromToken(token.substring(7));
    }
}
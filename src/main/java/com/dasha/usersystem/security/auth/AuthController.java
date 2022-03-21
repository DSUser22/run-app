package com.dasha.usersystem.security.auth;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.security.jwt.JWTUtility;
import com.dasha.usersystem.security.jwt.JWTResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    @Autowired
    private final JWTUtility jwtUtility;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "register")
    public String register(@RequestBody AuthRequest request){
        return authService.register(request);
    }

    @PostMapping(path = "auth")
    public JWTResponse auth(@RequestBody AuthRequest request){

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
    @GetMapping(path = "hello")
    public String hello(){
        return "hello, you're authorized";
    }


    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){
        return authService.confirmToken(token);
    }
}
package com.dasha.usersystem.security.auth;

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
    private final JWTUtility jwtUtility;
    private final ConfirmationTokenService confirmationTokenService;
    @Autowired
    private final AuthenticationManager authenticationManager;



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
    @DeleteMapping(path = "delete")
    public void delete(@RequestHeader(name="Authorization") String token){
        Long userId = jwtUtility.getIdFromToken(token);
        authService.deleteAppUser(userId);
    }
}
package com.dasha.usersystem.security.registration;

import com.dasha.usersystem.pattern.SignupRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // was (*)
public class RegistrationController {
    private final RegistrationService registrationService;
    @PostMapping(path = "signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest){
        return registrationService.register(signUpRequest);
    }
    @GetMapping(path = "confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}

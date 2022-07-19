package com.dasha.usersystem.appuser;

import com.dasha.usersystem.pattern.MessageResponse;
import com.dasha.usersystem.security.jwt.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/my")
@AllArgsConstructor
public class AdminController {
    private AppUserService service;
    private JwtUtility jwtUtility;

    @GetMapping(path = "users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> users(@RequestHeader(name="Authorization") String token){
        Long userId = jwtUtility.getIdFromJwtToken(token);
        return ResponseEntity.ok(new MessageResponse("Your id is: "+userId+"All users: "+service.findAllAppUsers()));
    }
}

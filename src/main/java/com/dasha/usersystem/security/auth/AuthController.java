package com.dasha.usersystem.security.auth;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.exception.login.TokenRefreshException;
import com.dasha.usersystem.pattern.JwtResponse;
import com.dasha.usersystem.pattern.LoginRequest;
import com.dasha.usersystem.pattern.MessageResponse;
import com.dasha.usersystem.refreshToken.RefreshToken;
import com.dasha.usersystem.refreshToken.RefreshTokenService;
import com.dasha.usersystem.security.jwt.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // was (*)
public class AuthController {

    private final AuthService authService;
    private AuthenticationManager authenticationManager;

    private RefreshTokenService refreshTokenService;

    private JwtUtility jwtUtility;

    @DeleteMapping(path = "delete")
    public ResponseEntity<?> delete(@RequestHeader(name="Authorization") String token){
        Long userId = jwtUtility.getIdFromJwtToken(token);
        authService.deleteAppUser(userId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUser user = (AppUser) authentication.getPrincipal();

        return getJwtResponse(user);
    }
    @PostMapping(path = "logout")
    public ResponseEntity<?> logout(@CookieValue("runyourbest")String refreshToken){
        refreshTokenService.deleteByToken(refreshToken);
        ResponseCookie cookie = jwtUtility.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new MessageResponse("You signed out. Cookies were deleted"));
    }
    @PostMapping(path = "refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "runyourbest") String refreshToken){
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getAppUser)
                .map(this::getJwtResponse).orElseThrow(() -> new TokenRefreshException("Refresh token wasn't found in DB"));
    }
    private ResponseEntity<JwtResponse> getJwtResponse(AppUser user) {
        refreshTokenService.deleteByAppUserId(user.getId());
        ResponseCookie jwtCookie = jwtUtility.generateJwtCookieWithRefreshToken(user);
        String accessToken = jwtUtility.generateAccessTokenFromUsername(user.getUsername(), user.getId());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new JwtResponse(accessToken));
    }
}
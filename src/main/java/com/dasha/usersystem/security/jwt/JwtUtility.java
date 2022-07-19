package com.dasha.usersystem.security.jwt;

import com.dasha.usersystem.appuser.AppUser;
import com.dasha.usersystem.refreshToken.RefreshToken;
import com.dasha.usersystem.refreshToken.RefreshTokenService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtility implements Serializable {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${app.jwtCookie}")
    private String jwtCookie;
    @Autowired
    private RefreshTokenService refreshTokenService;
    public ResponseCookie generateJwtCookieWithRefreshToken(AppUser userPrincipal) {
        // cookie - refresh token
        // body - access token

        Long userId = userPrincipal.getId();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, refreshToken.getToken())
                .path("/api/v1/auth").maxAge(100)
                .httpOnly(true).secure(false)
                .sameSite("strict").build();
        return cookie;
    }
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).maxAge(0)/*.path("/api/v1/auth")*/.build();
        return cookie;
    }
    public String generateAccessTokenFromUsername(String username, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userId);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    public Long getIdFromJwtToken(String token) {
        log.info("token {}", token);
        log.info("substring {}", token.substring(7));
        Integer userId =
                Jwts.parser().setSigningKey(jwtSecret)
                        .parseClaimsJws(token.substring(7))
                        .getBody().get("user_id", Integer.class);
        return userId.longValue();
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}

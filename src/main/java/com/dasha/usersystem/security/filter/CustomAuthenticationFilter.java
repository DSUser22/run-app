//package com.dasha.usersystem.security.filter;
package com.dasha.usersystem.security.filter;


/*import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dasha.usersystem.security.auth.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;
@Slf4j
@AllArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//        log.info("trying:");
//        log.info("email: {}", email);
//        log.info("password: {}", password);
//        UsernamePasswordAuthenticationToken token =
//                new UsernamePasswordAuthenticationToken(email, password);
//        return authManager.authenticate(token);
        try {
            System.out.println("в блоке try");
            String str = "hello";
            AuthRequest authenticationRequest
                    = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
            String email = authenticationRequest.getUsername();
            String password = authenticationRequest.getPassword();
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    password
            );
            System.out.println("authentication attempt: ");

            return authManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication auth)
            throws IOException, ServletException {
        User user = (User) auth.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+30*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+30*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        response.addHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);
    }
}*/
public class CustomAuthenticationFilter{}

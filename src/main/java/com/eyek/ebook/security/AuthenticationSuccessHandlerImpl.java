package com.eyek.ebook.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/*
Authentication success handler.
Generate the Json Web Token and send it back to the client.
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationTimeMillis}")
    private long expiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // generate jwt token on login success
        User user = authenticationFacade.getCurrentUser();
        AuthUserDto authUserDto = new AuthUserDto();
        authUserDto.setUsername(user.getUsername());

        long nowMillis = System.currentTimeMillis();
        String jwtToken = JWT.create()
                .withSubject(objectMapper.writeValueAsString(authUserDto))
                .withJWTId(UUID.randomUUID().toString())
                .withIssuedAt(new Date(nowMillis))
                .withExpiresAt(new Date(nowMillis + this.expiration))
                .sign(Algorithm.HMAC512(this.secret.getBytes()));

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jwtToken, HttpStatus.OK);
        objectMapper.writeValue(response.getWriter(), responseEntity);
    }
}

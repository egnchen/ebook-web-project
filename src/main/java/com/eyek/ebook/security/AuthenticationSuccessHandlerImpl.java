package com.eyek.ebook.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eyek.ebook.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    SecurityService securityService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        // on login success
        // generate jwt token
        User user = securityService.getCurrentUser();
        AuthUserDto authUserDto = new AuthUserDto();
        authUserDto.setUsername(user.getUsername());

        long nowMillis = System.currentTimeMillis();
        String jwtToken = JWT.create()
                .withSubject(objectMapper.writeValueAsString(authUserDto))
                .withJWTId(UUID.randomUUID().toString())
                .withIssuedAt(new Date(nowMillis))
                .withExpiresAt(new Date(nowMillis + this.expiration))
                .sign(Algorithm.HMAC512(this.secret.getBytes()));

        Map<String, String> responseEntity = new HashMap()  ;
        responseEntity.put("status", "OK");
        responseEntity.put("token", jwtToken);

        objectMapper.writeValue(httpServletResponse.getWriter(), jwtToken);
    }
}

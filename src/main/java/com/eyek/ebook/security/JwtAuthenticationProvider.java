package com.eyek.ebook.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/*
JWT authentication provider.
This provider resides along side the default DaoAuthenticationProvider based on username and password.
reference: https://www.logicbig.com/tutorials/spring-framework/spring-security/custom-authentication-provider.html
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // if such type of authentication is not supported, return null
        if(!(authentication instanceof JwtAuthenticationToken))
            return null;
        String token = authentication.getCredentials().toString();
        try {
            JWT.require(Algorithm.HMAC512(this.secret.getBytes())).build().verify(token);
            authentication.setAuthenticated(true);
            return authentication;
        } catch(JWTVerificationException e) {
            // verification failed
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(JwtAuthenticationToken.class);
    }
}

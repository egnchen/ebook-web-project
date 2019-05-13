package com.eyek.ebook.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/*
Authentication token implementation based on JWT.
Save Json Web Token as credential.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private final UserDetails principal;

    public JwtAuthenticationToken(UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}

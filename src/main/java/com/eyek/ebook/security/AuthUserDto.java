package com.eyek.ebook.security;

/*
JWT payload subject.
 */
public class AuthUserDto {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

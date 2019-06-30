package com.eyek.ebook.controller.dto;


import com.eyek.ebook.model.User;

public class UserProfileDto {
    private String username;
    private String email;
    private User.Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}

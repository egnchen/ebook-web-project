package com.eyek.ebook.controller.dto;

import com.eyek.ebook.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewUserDto {
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String passwordNotEncrypted;

    @NotNull
    private User.Role role = User.Role.ROLE_USER;

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

    public String getPasswordNotEncrypted() {
        return passwordNotEncrypted;
    }

    public void setPasswordNotEncrypted(String passwordNotEncrypted) {
        this.passwordNotEncrypted = passwordNotEncrypted;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}

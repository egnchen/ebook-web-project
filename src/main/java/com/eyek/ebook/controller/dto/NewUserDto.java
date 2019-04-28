package com.eyek.ebook.controller.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

public class NewUserDto {
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String passwordNotEncrypted;

    @Column(nullable = true)
    private List<String> roles = Arrays.asList("ROLE_USER");

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}

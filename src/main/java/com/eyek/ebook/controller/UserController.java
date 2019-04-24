package com.eyek.ebook.controller;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.controller.dto.UserProfileDto;
import com.eyek.ebook.service.SecurityService;
import com.eyek.ebook.service.UserService;
import com.eyek.ebook.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/profile")
    public UserProfileDto getCurrentUser() {
        return userService.getUserProfile(securityService.getCurrentUser());
    }

    @PostMapping("/register")
    public Message register(@RequestBody @Valid NewUserDto newUser) {
        userService.save(newUser);
        return new Message("OK", null);
    }

}

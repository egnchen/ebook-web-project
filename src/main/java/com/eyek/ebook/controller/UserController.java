package com.eyek.ebook.controller;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.controller.dto.UserProfileDto;
import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @GetMapping("/profile")
    public UserProfileDto getCurrentUser() {
        return userService.getUserProfile(authenticationFacade.getCurrentUser());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid NewUserDto newUser) {
        userService.save(newUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

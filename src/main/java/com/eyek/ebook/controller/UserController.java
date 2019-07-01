package com.eyek.ebook.controller;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.controller.dto.UserProfileDto;
import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @GetMapping("/profile")
    public UserProfileDto getCurrentUser() {
        return userService.getUserProfile(authenticationFacade.getCurrentUser().getId());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid NewUserDto newUser) {
        userService.save(newUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/manage/list")
    public ResponseEntity<List<UserProfileDto>> getUserList() {
        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/manage/disable")
    public ResponseEntity<String> disableUser(@RequestParam int id) {
        try {
            if (userService.setUserStatus(id, false))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/manage/enable")
    public ResponseEntity<String> enableUser(@RequestParam int id) {
        try {
            if (userService.setUserStatus(id, true))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

package com.eyek.ebook.controller;

import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.UserRepository;
import com.eyek.ebook.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            User user = userRepository.findByUsername(username);
            user.setPassword(null);
            return user;
        } else return null;
    }

    @PostMapping("/register")
    public Message register(@RequestParam @Valid User newUser) {

        if(newUser.getRole() == User.UserRole.ADMIN)
            return new Message("Operation not permitted", "You're not allowed to create admin users.");
        if(userRepository.findByUsername(newUser.getUsername()) != null)
            return new Message("Operation not permitted", "Username already exists.");
        userRepository.save(newUser);
        return new Message("OK", null);
    }
}

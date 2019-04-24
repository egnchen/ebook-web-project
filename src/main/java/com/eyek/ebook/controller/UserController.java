package com.eyek.ebook.controller;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.facade.LoggerFacade;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.UserRepository;
import com.eyek.ebook.service.UserService;
import com.eyek.ebook.util.Message;
import com.eyek.ebook.util.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public User getCurrentUser(Principal principal) {
        LoggerFacade.getLogger().info(principal.toString());
        if(principal instanceof SecurityUser) {
            String username = ((UserDetails)principal).getUsername();
            User user = userRepository.findByUsername(username);
            user.setPassword(null);
            return user;
        } else return null;
    }

    @PostMapping("/register")
    public Message register(@RequestBody @Valid NewUserDto newUser) {
        userService.save(newUser);
        return new Message("OK", null);
    }

}

package com.eyek.ebook.controller;

import com.eyek.ebook.facade.LoggerFacade;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.UserRepository;
import com.eyek.ebook.service.SecurityService;
import com.eyek.ebook.service.UserService;
import com.eyek.ebook.util.Message;
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

    @Autowired
    private SecurityService securityService;

    @GetMapping("/user/profile")
    public User getCurrentUser(Principal principal) {
        LoggerFacade.getLogger().info(principal.toString());
        if(principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            User user = userRepository.findByUsername(username);
            user.setPassword(null);
            return user;
        } else return null;
    }

    @PostMapping("/register")
    public Message register(@RequestBody @Valid User newUser) {
        if(newUser.getRoles().isEmpty()) {
        }
        userService.save(newUser);
//        if(newUser.getRole() == User.UserRole.ADMIN)
//            return new Message("Operation not permitted", "You're not allowed to create admin users.");
//        if(userRepository.findByUsername(newUser.getUsername()) != null)
//            return new Message("Operation not permitted", "Username already exists.");
        userRepository.save(newUser);
        return new Message("OK", null);
    }

    @PostMapping("/auth/post-login")
    public String postLogin(@RequestBody String username, @RequestParam String password) {
        // save session
        LoggerFacade.getLogger().debug("postLogin: saving login session information.");
        return "OK";
    }

}

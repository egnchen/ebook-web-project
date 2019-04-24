package com.eyek.ebook.facade;

import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.UserRepository;
import com.eyek.ebook.service.UserService;
import com.eyek.ebook.util.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getCurrentUser() {
        Object principal = getAuthentication().getPrincipal();
        if(principal instanceof SecurityUser) {
            System.out.println("Got user, username = " + ((SecurityUser) principal).getUsername());
            return ((SecurityUser)getAuthentication().getPrincipal()).getUser();
        } else {
            // anonymous user
            System.out.println("Anonymous user");
            return null;
        }
    }
}
package com.eyek.ebook.facade;

import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getCurrentUser() {
        if(getAuthentication().isAuthenticated()) {
            String userName = ((UserDetails)getAuthentication().getPrincipal()).getUsername();
            return userRepository.findByUsername(userName);
        } else
            return null;
    }
}
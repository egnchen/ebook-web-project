package com.eyek.ebook.facade;

import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            LoggerFacade.getLogger().debug(getAuthentication().getPrincipal().toString());
            // String userName = ((UserDetails)getAuthentication().getPrincipal()).getUsername();
            String userName = "admin";
            return userRepository.findByUsername(userName);
        } else
            return null;
    }
}
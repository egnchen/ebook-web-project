package com.eyek.ebook.facade;

import com.eyek.ebook.model.User;
import com.eyek.ebook.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getCurrentUser() {
        Object principal = getAuthentication().getPrincipal();
        if(principal instanceof SecurityUser) {
            return ((SecurityUser)principal).getUser();
        } else {
            // anonymous user
            return null;
        }
    }

    @Override
    public String getCurrentUsername() {
        User currentUser = getCurrentUser();
        if(currentUser != null)
            return currentUser.getUsername();
        else
            return null;
    }
}
package com.eyek.ebook.service;

import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.facade.LoggerFacade;
import com.eyek.ebook.model.User;
import com.eyek.ebook.util.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String getLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }
        return null;
    }

    @Override
    public void login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            LoggerFacade.getLogger().info(String.format("Login %s successfully!", username));
        } else {
            LoggerFacade.getLogger().info(String.format("Login %s failed.", username));
        }
    }

    @Override
    public User getCurrentUser() {
        Object principal = authenticationFacade.getAuthentication().getPrincipal();
        if(principal instanceof SecurityUser) {
            return ((SecurityUser)principal).getUser();
        } else {
            // anonymous user
            return null;
        }
    }
}

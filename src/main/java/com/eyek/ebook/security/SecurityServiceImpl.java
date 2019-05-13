package com.eyek.ebook.security;

import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String getLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }
        return null;
    }

//    @Override
//    public boolean login(String username, String password) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//
//        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//
//        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
//            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            System.out.println(String.format("Login %s successfully!", username));
//            return true;
//        } else {
//            LoggerFacade.getLogger(this).info(String.format("Login %s failed.", username));
//            return false;
//        }
//    }

//    @Override
//    public boolean loginJwt(String jwtToken) {
//        try {
//            DecodedJWT decodedJWT = JWT.decode(jwtToken);
//            String username = objectMapper.readValue(decodedJWT.getSubject(), AuthUserDto.class).getUsername();
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            JwtAuthenticationToken authentication = new JwtAuthenticationToken(userDetails);
//            authentication.setToken(jwtToken);
//
//            if(authentication.isAuthenticated()) {
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                LoggerFacade.getLogger(this).info(String.format("Login %s successfully!", username));
//                return true;
//            } else {
//                System.out.println(String.format("Login %s failed.", username));
//                return false;
//            }
//        } catch(JWTDecodeException e) {
//            e.printStackTrace();
//            return false;
//        } catch(Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

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

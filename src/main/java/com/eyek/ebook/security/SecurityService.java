package com.eyek.ebook.security;

import com.eyek.ebook.model.User;

public interface SecurityService {

    String getLoggedInUsername();
    User getCurrentUser();

    //boolean login(String username, String password);
    boolean loginJwt(String jwtToken);
}
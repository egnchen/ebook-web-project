package com.eyek.ebook.service;

import com.eyek.ebook.model.User;

public interface SecurityService {

    String getLoggedInUsername();
    User getCurrentUser();

    void login(String username, String password);
}
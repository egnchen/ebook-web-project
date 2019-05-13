package com.eyek.ebook.security;

import com.eyek.ebook.model.User;

public interface SecurityService {

    String getLoggedInUsername();
    User getCurrentUser();
}
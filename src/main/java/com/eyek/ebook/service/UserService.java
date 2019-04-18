package com.eyek.ebook.service;

import com.eyek.ebook.model.User;

public interface UserService {

    void save(User user);
    User findByUsername(String username);
}

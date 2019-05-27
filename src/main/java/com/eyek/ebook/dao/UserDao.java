package com.eyek.ebook.dao;

import com.eyek.ebook.model.User;

import javax.validation.constraints.Email;

public interface UserDao {
    User getUserByUsername(String username);
    User getUserByEmail(@Email String email);

    User saveUser(User user);
}

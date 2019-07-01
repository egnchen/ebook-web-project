package com.eyek.ebook.dao;

import com.eyek.ebook.model.User;

import javax.validation.constraints.Email;
import java.util.List;

public interface UserDao {
    User getUser(int userId);
    User getUserByUsername(String username);
    User getUserByEmail(@Email String email);
    List<User> getAllUsers();

    User saveUser(User user);
}

package com.eyek.ebook.service;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.controller.dto.UserProfileDto;
import com.eyek.ebook.model.User;

public interface UserService {

    void save(NewUserDto user);
    User findByUsername(String username);
    UserProfileDto getUserProfile(User user);
}

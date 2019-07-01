package com.eyek.ebook.service;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.controller.dto.UserProfileDto;
import com.eyek.ebook.model.User;

import java.util.List;

public interface UserService {

    User findById(int id);
    User findByUsername(String username);
    List<UserProfileDto> listAllUsers();
    UserProfileDto getUserProfile(int userId);

    void save(NewUserDto user);
    boolean setUserStatus(int userId, boolean enabled);
}

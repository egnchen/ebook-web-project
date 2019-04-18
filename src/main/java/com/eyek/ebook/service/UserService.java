package com.eyek.ebook.service;

import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User registerNewUserAccount(User newUser) {
        return null;
    }
}

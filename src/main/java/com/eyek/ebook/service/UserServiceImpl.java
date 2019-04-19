package com.eyek.ebook.service;

import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.RoleRepository;
import com.eyek.ebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void save(@Valid User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if(userRepository.findByUsername(user.getUsername()) == null &&
                userRepository.findByEmail(user.getEmail()) == null)
            userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

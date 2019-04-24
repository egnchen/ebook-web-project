package com.eyek.ebook.service;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.model.Role;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.RoleRepository;
import com.eyek.ebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(@Valid NewUserDto newUserDto) {
        if(userRepository.findByUsername(newUserDto.getUsername()) == null &&
            userRepository.findByEmail(newUserDto.getEmail()) == null) {
            User newUser = new User();
            newUser.setUsername(newUserDto.getUsername());
            newUser.setEmail(newUserDto.getEmail());
            newUser.setPassword(passwordEncoder.encode(newUserDto.getPasswordNotEncrypted()));
            Set<Role> roleSet = new HashSet<Role>();
            for (String roleString: newUserDto.getRoles()) {
                roleSet.add(roleRepository.findByName(roleString));
            }
            newUser.setRoles(roleSet);
            newUser.setEnabled(true);
            userRepository.save(newUser);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

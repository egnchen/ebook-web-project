package com.eyek.ebook.service;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.controller.dto.UserProfileDto;
import com.eyek.ebook.dao.UserDao;
import com.eyek.ebook.model.Role;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.RoleRepository;
import org.modelmapper.ModelMapper;
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
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void save(@Valid NewUserDto newUserDto) {
        if(userDao.getUserByUsername(newUserDto.getUsername()) == null &&
            userDao.getUserByEmail(newUserDto.getEmail()) == null) {
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
            userDao.saveUser(newUser);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public UserProfileDto getUserProfile(User user) {
        UserProfileDto userProfileDto = modelMapper.map(user, UserProfileDto.class);
        return userProfileDto;
    }
}

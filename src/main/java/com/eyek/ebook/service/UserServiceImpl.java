package com.eyek.ebook.service;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.controller.dto.UserProfileDto;
import com.eyek.ebook.dao.UserDao;
import com.eyek.ebook.facade.AuthenticationFacade;
import com.eyek.ebook.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationFacade authenticationFacade;

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
            newUser.setRole(newUserDto.getRole());
            newUser.setEnabled(true);
            userDao.saveUser(newUser);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public UserProfileDto getUserProfile(int userId) {
        User user = userDao.getUser(userId);
        UserProfileDto userProfileDto = modelMapper.map(user, UserProfileDto.class);
        return userProfileDto;
    }

    @Override
    public boolean setUserStatus(int userId, boolean enabled) {
        User user = userDao.getUser(userId);
        if (user == null)
            throw new RuntimeException("User not found.");
        if (user.getRole() == User.Role.ROLE_ADMIN)
            throw new RuntimeException("You cannot modify an admin user's status.");
        if (user.getId() == authenticationFacade.getCurrentUser().getId())
            throw new RuntimeException("You cannot modify your own status.");

        if (user.isEnabled() == enabled)
            return false;
        user.setEnabled(enabled);
        userDao.saveUser(user);
        return true;
    }

}

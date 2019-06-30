package com.eyek.ebook.config;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.facade.LoggerFacade;
import com.eyek.ebook.model.User;
import com.eyek.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        LoggerFacade.getLogger(this).info("Seeding default user...");

        NewUserDto user = new NewUserDto();
        user.setUsername("admin");
        user.setPasswordNotEncrypted("adminSecret");
        user.setEmail("admin@ebook.com");
        user.setRole(User.Role.ROLE_ADMIN);
        userService.save(user);

        alreadySetup = true;
    }
}
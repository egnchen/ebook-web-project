package com.eyek.ebook.config;

import com.eyek.ebook.controller.dto.NewUserDto;
import com.eyek.ebook.facade.LoggerFacade;
import com.eyek.ebook.model.Role;
import com.eyek.ebook.repository.RoleRepository;
import com.eyek.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        LoggerFacade.getLogger().info("Seeding default user...");

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        Role userRole = createRoleIfNotFound("ROLE_USER");

        NewUserDto user = new NewUserDto();
        user.setUsername("admin");
        user.setPasswordNotEncrypted("adminSecret");
        user.setEmail("admin@ebook.com");
        user.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
        userService.save(user);

        alreadySetup = true;
    }

    @Transactional
    protected Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
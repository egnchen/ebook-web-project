package com.eyek.ebook.config;

import com.eyek.ebook.model.Role;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.RoleRepository;
import com.eyek.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        Role userRole = createRoleIfNotFound("ROLE_USER");

        User user = new User();
        user.setUsername("admin");
        user.setPassword("adminSecret");
        user.setEmail("admin@ebook.com");
        user.setRoles(Set.of(adminRole, userRole));
        user.setEnabled(true);
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
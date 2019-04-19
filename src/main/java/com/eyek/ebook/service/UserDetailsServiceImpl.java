package com.eyek.ebook.service;

import com.eyek.ebook.model.Role;
import com.eyek.ebook.model.User;
import com.eyek.ebook.repository.RoleRepository;
import com.eyek.ebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Primary
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public boolean registerNewUserAccount(User newUser) {
        if(userRepository.findByUsername(newUser.getUsername()) != null)
            return false;
        if(userRepository.findByEmail(newUser.getEmail()) != null)
            return false;
        if(newUser.getRoles().isEmpty())
            newUser.setRoles(Set.of(roleRepository.findByName("ROLE_ADMIN")));

        return true;
    }
}

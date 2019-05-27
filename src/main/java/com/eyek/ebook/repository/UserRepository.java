package com.eyek.ebook.repository;

import com.eyek.ebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;

// automatically implemented by spring
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(@Email String email);
}

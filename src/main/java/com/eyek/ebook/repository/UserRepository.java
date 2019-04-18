package com.eyek.ebook.repository;

import com.eyek.ebook.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.validation.Valid;

// automatically implemented by spring
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(@Valid String email);
}

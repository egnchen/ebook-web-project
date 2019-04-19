package com.eyek.ebook.facade;

import com.eyek.ebook.model.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();

    User getCurrentUser();
}

package com.eyek.ebook.service;

public interface SecurityService {

    String getLoggedInUsername();

    void login(String username, String password);
}
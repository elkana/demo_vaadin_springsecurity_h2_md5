package com.example.demovaadin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.vaadin.flow.spring.security.AuthenticationContext;

@Service
public class SecurityService {

    @Autowired AuthenticationContext authenticationContext;

    public UserDetails getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class).get();
    }

    public void logout() {
        authenticationContext.logout();
    }
}
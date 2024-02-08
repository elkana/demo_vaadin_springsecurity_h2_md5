package com.example.demovaadin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.demovaadin.model.McUser;
import com.example.demovaadin.repo.UserRepo;
import com.vaadin.flow.spring.security.AuthenticationContext;

@Service
public class SecurityService {

    @Autowired
    AuthenticationContext authContext;

    @Autowired UserRepo repoUser;

    public UserDetails getAuthenticatedUser() {
        // return authContext.getAuthenticatedUser(UserDetails.class).get(); dangerous
        var context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) context.getAuthentication().getPrincipal();
        }
        // Anonymous or no authentication.
        return null;          
    }

    public void logout() {
        authContext.logout();
    }

    public boolean isAdmin() {
        return  getAuthenticatedUser().getAuthorities().stream().anyMatch(a -> a.getAuthority().startsWith("ROLE_ADM"));
        // return  getAuthenticatedUser().getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ROLE_ADM".equals(a.getAuthority()));
    }

    public McUser getUserDetail() {
        return repoUser.findById(getAuthenticatedUser().getUsername()).get();
    }
}

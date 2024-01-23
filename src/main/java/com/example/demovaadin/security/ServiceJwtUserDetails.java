package com.example.demovaadin.security;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demovaadin.common.Constants;
import com.example.demovaadin.model.McUser;
import com.example.demovaadin.repo.UserRepo;

@Service
public class ServiceJwtUserDetails implements UserDetailsService {
    @Autowired
    UserRepo repoUser;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        McUser tblUser = repoUser.findById(username).orElseThrow(
                // () -> new UsernameNotFoundException("Mismatch UserId or Password"));
                () -> new UsernameNotFoundException(Constants.MSG_UNAUTHORIZED));
        return new User(tblUser.getUserId(), tblUser.getEncryptedPwd(),
                java.util.Collections.emptyList());
    }

    // reserved
    // private static List<GrantedAuthority> getAuthorities(User user) {
    //     return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
    //             .collect(Collectors.toList());

    // }    
}

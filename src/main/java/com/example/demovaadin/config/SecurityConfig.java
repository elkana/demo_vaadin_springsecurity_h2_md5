package com.example.demovaadin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.demovaadin.security.JwtRequestFilter;
import com.example.demovaadin.view.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**", "/icons/**", "/images/**", "/styles/**")
                .permitAll())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        // .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll());

        super.configure(http);
        setLoginView(http, LoginView.class);
        // why http splitted ? read
        // https://stackoverflow.com/questions/74582626/vaadin-websecurity-connect-to-h2-database-forbidden
        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions(f -> f.disable()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // https://stackoverflow.com/questions/49374506/method-md5passwordencoder-doesnt-work-for-me-in-spring5
        // spring says md5 is not secured anymore
        return new MessageDigestPasswordEncoder("MD5");
    }

    // @Bean
    // public AuthenticationManager authenticationManager(
    // final AuthenticationConfiguration authenticationConfiguration) throws Exception {
    // return authenticationConfiguration.getAuthenticationManager();
    // }

    // @Bean
    // public JwtRequestFilter jwtRequestFilter(){
    // return new JwtRequestFilter();
    // }

    /**
     * Allows access to static resources, bypassing Spring Security.
     */
    // @Override
    // public void configure(WebSecurity web) {
    // web.ignoring().requestMatchers(
    // // Client-side JS
    // "/VAADIN/**",
    // // the standard favicon URI
    // "/favicon.ico",
    // // the robots exclusion standard
    // "/robots.txt",
    // // web application manifest
    // "/manifest.webmanifest", "/sw.js", "/offline.html",
    // // icons and images
    // "/icons/**", "/images/**", "/styles/**",
    // // (development mode) H2 debugging console
    // "/h2-console/**");
    // }

    // @Bean
    // UserDetailsManager userDetailsManager() {
    // return new InMemoryUserDetailsManager(
    // User.withUsername("elkana911").password("{noop}elkana911").build());
    // }
}

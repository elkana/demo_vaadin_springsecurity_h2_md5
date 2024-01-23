package com.example.demovaadin.security;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Value("${uri.nonjwt}")
    List<String> bypassUri;

    @Autowired JwtTokenService jwtTokenService;
    @Autowired ServiceJwtUserDetails jwtUserDetailsService;

    // client kadang tetap menyertakan authorization di header di login, maka perlu di filter lagi supaya bisa diskip
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return bypassUri.stream().anyMatch(u -> new AntPathMatcher().match(u, request.getServletPath()));
    }
    
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain chain) throws ServletException, IOException {
        // look for Bearer auth header
        val header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        val token = header.substring(7);
        val username = jwtTokenService.validateTokenAndGetUsername(token);
        if (username == null) {
            chain.doFilter(request, response);
            return;
        }
        // set user details on spring security context
        val userDetails = jwtUserDetailsService.loadUserByUsername(username);
        val authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
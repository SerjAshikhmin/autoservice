package com.senla.courses.config.security.filters;

import com.senla.courses.autoservice.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;


public class JwtBasedAuthenticationFilter extends UsernamePasswordAuthenticationFilter {//AbstractAuthenticationProcessingFilter {

    private final TokenService tokenService;

    public JwtBasedAuthenticationFilter(AuthenticationManager authManager, TokenService tokenService) {//(String url, HttpMethod httpMethod, AuthenticationManager authManager) {
        setAuthenticationManager(authManager);
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getParameter("username"),
                            request.getParameter("password"),
                            Collections.emptySet()
                    )
            );
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = tokenService.generateToken(user.getUsername(), LocalDateTime.now().plusMinutes(30));
        response.addHeader(HttpHeaders.AUTHORIZATION, token);
    }

}

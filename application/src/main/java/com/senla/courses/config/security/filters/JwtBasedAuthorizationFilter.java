package com.senla.courses.config.security.filters;

import com.senla.courses.autoservice.service.TokenService;
import com.senla.courses.autoservice.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtBasedAuthorizationFilter extends BasicAuthenticationFilter {

    private final TokenService tokenService;
    private final UserService userService;

    public JwtBasedAuthorizationFilter(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService) {
        super(authenticationManager);
        this.tokenService = tokenService;
        this.userService = userService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Authentication authentication = null;
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            String userName = tokenService.getUserNameFromToken(token);
            authentication = new UsernamePasswordAuthenticationToken(userName, null, userService.getRolesByUser(userName));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
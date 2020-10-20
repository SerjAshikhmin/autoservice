package com.senla.courses.config.security;

import com.senla.courses.autoservice.dao.interfaces.IRoleDao;
import com.senla.courses.autoservice.dao.interfaces.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IRoleDao roleDao;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        UserDetails user =  userDao.findByUserName(auth.getName());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        String password = auth.getCredentials().toString();
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

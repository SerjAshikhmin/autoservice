package com.senla.courses.autoservice.service;

import com.senla.courses.autoservice.dao.interfaces.IRoleDao;
import com.senla.courses.autoservice.dao.interfaces.IUserDao;
import com.senla.courses.autoservice.dao.jpadao.DbJpaConnector;
import com.senla.courses.autoservice.model.security.Role;
import com.senla.courses.autoservice.model.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityTransaction;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IRoleDao roleDao;
    @Autowired
    DbJpaConnector dbJpaConnector;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userDao.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails authUser = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .authorities(user.getAuthorities())
                .build();
        return authUser;
    }

    public void addUser(User user) {
        EntityTransaction transaction = dbJpaConnector.getTransaction();
        try {
            transaction.begin();
            userDao.addUser(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage());
        } finally {
            dbJpaConnector.closeSession();
        }
    }

    public void addRole(Role role) {
        EntityTransaction transaction = dbJpaConnector.getTransaction();
        try {
            transaction.begin();
            roleDao.addRole(role);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage());
        } finally {
            dbJpaConnector.closeSession();
        }
    }
}

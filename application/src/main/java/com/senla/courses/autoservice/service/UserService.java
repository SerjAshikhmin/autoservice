package com.senla.courses.autoservice.service;

import com.senla.courses.autoservice.dao.interfaces.IRoleDao;
import com.senla.courses.autoservice.dao.interfaces.IUserDao;
import com.senla.courses.autoservice.dao.jpadao.DbJpaConnector;
import com.senla.courses.autoservice.dto.UserDto;
import com.senla.courses.autoservice.dto.mappers.MasterMapper;
import com.senla.courses.autoservice.dto.mappers.UserMapper;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterNotFoundException;
import com.senla.courses.autoservice.model.security.Role;
import com.senla.courses.autoservice.model.security.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IRoleDao roleDao;
    @Autowired
    DbJpaConnector dbJpaConnector;
    @Autowired
    private UserMapper userMapper;
    private PasswordEncoder encoder;

    public UserService() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String userName) throws UsernameNotFoundException {
        UserDetails user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        UserDetails authUser = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .authorities(user.getAuthorities())
                .build();
        return authUser;
    }

    public void addUser(@NonNull User user) {
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

    public void addRole(@NonNull Role role) {
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

    private List<User> getAllUsers() throws MasterNotFoundException {
        try {
            List<User> users = userDao.getAllUsers();
            if (users == null || users.isEmpty()) {
                throw new RuntimeException("Users not found");
            }
            return users;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MasterNotFoundException(e.getMessage());
        }
    }

    public List<UserDto> getAllDtoUsers() throws MasterNotFoundException {
        return userMapper.userListToUserDtoList(getAllUsers());
    }

    public Collection<? extends GrantedAuthority> getRolesByUser(String userName) {
        UserDetails user = userDao.findByUserName(userName);
        return user.getAuthorities();
    }
}

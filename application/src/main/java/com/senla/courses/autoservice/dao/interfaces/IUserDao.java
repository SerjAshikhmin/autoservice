package com.senla.courses.autoservice.dao.interfaces;

import com.senla.courses.autoservice.model.domain.Master;
import com.senla.courses.autoservice.model.security.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.List;


@Repository
public interface IUserDao {

    UserDetails findByUserName(String userName) throws PersistenceException;
    void addUser(User user) throws PersistenceException;
    List<User> getAllUsers() throws PersistenceException;
}

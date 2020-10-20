package com.senla.courses.autoservice.dao.interfaces;

import com.senla.courses.autoservice.model.security.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;


@Repository
public interface IRoleDao {

    void addRole(Role role) throws PersistenceException;
}

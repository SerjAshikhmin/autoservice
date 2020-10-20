package com.senla.courses.autoservice.dao;

import com.senla.courses.autoservice.dao.interfaces.IRoleDao;
import com.senla.courses.autoservice.dao.jpadao.AbstractJpaDao;
import com.senla.courses.autoservice.model.security.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;


@Repository
public class RoleDao extends AbstractJpaDao<Role> implements IRoleDao {

    @Override
    public void addRole(Role role) throws PersistenceException {
        insert(role);
    }
}

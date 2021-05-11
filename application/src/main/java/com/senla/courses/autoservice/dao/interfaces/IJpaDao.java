package com.senla.courses.autoservice.dao.interfaces;

import javax.persistence.PersistenceException;
import java.util.List;


public interface IJpaDao<T> {

    int insert(T obj) throws PersistenceException;
    int delete(T obj) throws PersistenceException;
    int update(T obj) throws PersistenceException;
    T findById(int id) throws PersistenceException;
    List<T> findAll() throws PersistenceException;
}

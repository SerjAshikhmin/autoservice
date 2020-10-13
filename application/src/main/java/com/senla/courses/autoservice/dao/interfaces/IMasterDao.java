package com.senla.courses.autoservice.dao.interfaces;

import com.senla.courses.autoservice.exceptions.masterexceptions.MasterNotFoundException;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.List;


@Repository
public interface IMasterDao {

    int addMaster(Master master) throws PersistenceException;
    int removeMaster(Master master) throws PersistenceException;
    Master getMasterById(int id) throws PersistenceException;
    List<Master> getAllMasters() throws PersistenceException;
    void setAllMasters(List<Master> allMasters);
    int updateMaster(Master master) throws PersistenceException;
    Order getCurrentOrder(Master master) throws MasterNotFoundException, PersistenceException;

}

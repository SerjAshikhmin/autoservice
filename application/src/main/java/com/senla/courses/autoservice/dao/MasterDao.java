package com.senla.courses.autoservice.dao;

import com.senla.courses.autoservice.dao.interfaces.IMasterDao;
import com.senla.courses.autoservice.dao.interfaces.IOrderDao;
import com.senla.courses.autoservice.dao.jpadao.AbstractJpaDao;
import com.senla.courses.autoservice.model.domain.Master;
import com.senla.courses.autoservice.model.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.util.List;


@Repository
public class MasterDao extends AbstractJpaDao<Master> implements IMasterDao {

    @Autowired
    IOrderDao orderDao;

    @Override
    public int addMaster(Master master) throws PersistenceException {
        return insert(master);
    }

    @Override
    public int removeMaster(Master master) throws PersistenceException {
        return delete(master);
    }

    @Override
    public Master getMasterById(int id) throws PersistenceException {
        return findById(id);
    }

    @Override
    public List<Master> getAllMasters() throws PersistenceException {
        return findAll();
    }

    @Override
    public void setAllMasters(List<Master> allMasters) {
        //this.masters = allMasters;
    }

    @Override
    public int updateMaster(Master master) throws PersistenceException {
        return update(master);
    }

    public Order getCurrentOrder(Master master) throws PersistenceException {
        if (master != null) {
            return orderDao.getOrderById(master.getOrderId());
        } else {
            throw new PersistenceException("Master not found");
        }
    }

}

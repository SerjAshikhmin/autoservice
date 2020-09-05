package com.senla.courses.autoservice.dao;

import com.lib.dicontainer.annotations.InjectByType;
import com.senla.courses.autoservice.dao.interfaces.IMasterDao;
import com.senla.courses.autoservice.dao.interfaces.IOrderDao;
import com.senla.courses.autoservice.dao.jpadao.AbstractJpaDao;
import com.senla.courses.autoservice.exceptions.MasterNotFoundException;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

public class MasterDao extends AbstractJpaDao<Master> implements IMasterDao {

    @InjectByType
    IOrderDao orderDao;

    public MasterDao() {
        super(Master.class);
    }

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
        /*Master daoMaster = getMasterById(master.getId());
        return updateMasterFields(master, daoMaster);*/
    }

    public Order getCurrentOrder(Master master) throws MasterNotFoundException, PersistenceException {
        if (master != null) {
            return orderDao.getOrderById(master.getOrderId());
        } else {
            throw new MasterNotFoundException();
        }
    }

    @Override
    public List<Master> getAllFreeMasters() throws PersistenceException {
        return getAllMasters().stream()
                .filter(master -> !master.isBusy())
                .collect(Collectors.toList());
    }

}

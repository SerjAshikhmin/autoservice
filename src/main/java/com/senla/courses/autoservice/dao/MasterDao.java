package com.senla.courses.autoservice.dao;

import com.senla.courses.autoservice.dao.interfaces.IMasterDao;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;

import java.util.ArrayList;
import java.util.List;

public class MasterDao implements IMasterDao {

    private List<Master> masters = new ArrayList<>();

    @Override
    public boolean addMaster(Master master) {
        return this.masters.add(master);
    }

    @Override
    public boolean removeMaster(Master master) {
        return this.masters.remove(master);
    }

    @Override
    public Master getMasterById(int id) {
        for (Master master : masters) {
            if (master.getId() == id) {
                return master;
            }
        }
        return null;
    }

    @Override
    public List<Master> getAllMasters() {
        return masters;
    }

    @Override
    public Master updateMaster(Master master) {
        Master daoMaster = getMasterById(master.getId());
        return updateMasterFields(master, daoMaster);
    }

    public Order getCurrentOrder(Master master) {
        return master.getCurrentOrder();
    }

    @Override
    public List<Master> getAllFreeMasters() {
        List<Master> freeMasters = new ArrayList<>();
        for (Master master : getAllMasters()) {
            if (!master.isBusy()) {
                freeMasters.add(master);
            }
        }
        return freeMasters;
    }

    private Master updateMasterFields(Master master, Master daoMaster) {
        daoMaster.setCategory(master.getCategory());
        daoMaster.setBusy(master.isBusy());
        return daoMaster;
    }
}
package com.senla.courses.autoservice.service;

import com.lib.dicontainer.annotations.InjectByType;
import com.lib.utils.CsvUtil;
import com.lib.utils.exceptions.WrongFileFormatException;
import com.senla.courses.autoservice.dao.interfaces.IMasterDao;
import com.senla.courses.autoservice.dao.interfaces.IOrderDao;
import com.senla.courses.autoservice.exceptions.MasterNotFoundException;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import com.senla.courses.autoservice.service.comparators.master.MasterByBusyComparator;
import com.senla.courses.autoservice.service.comparators.master.MasterByNameComparator;
import com.senla.courses.autoservice.service.interfaces.IMasterService;
import com.senla.courses.autoservice.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MasterService implements IMasterService {

    @InjectByType
    private IMasterDao masterDao;
    @InjectByType
    private IOrderDao orderDao;
    private static final Logger logger = LoggerFactory.getLogger(MasterService.class);

    @Override
    public int addMaster(int id, String name, int category) {
        Master master = new Master (id, name, category);
        try {
            return masterDao.addMaster(master);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public int removeMaster(String name) {
        try {
            Master master = findMasterByName(name);
            if (master == null) {
                logger.error("Мастера с указанным именем не существует");
                return 0;
            }
            return masterDao.removeMaster(master);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public int updateMaster(Master master) {
        try {
            return masterDao.updateMaster(master);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    @Override
    public List<Master> getAllMasters() {
        try {
            return masterDao.getAllMasters();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } return null;
    }

    @Override
    public List<Master> getAllMastersSorted(String sortBy) {
        List<Master> allMastersSorted = new ArrayList<>();
        try {
            allMastersSorted.addAll(masterDao.getAllMasters());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        Comparator masterComparator = getMasterComparator(sortBy);
        if (masterComparator != null) {
            allMastersSorted.sort(masterComparator);
        }
        return allMastersSorted;
    }

    @Override
    public List<Master> getAllFreeMasters() {
        try {
            return masterDao.getAllFreeMasters();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Order getCurrentOrder(String name) {
        try {
            return masterDao.getCurrentOrder(findMasterByName(name));
        } catch (MasterNotFoundException e) {
            logger.error("Мастер не найден");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Master findMasterByName(String name) {
        for (Master master : getAllMasters()) {
            if (master.getName().equals(name)) {
                return master;
            }
        }
        return null;
    }

    @Override
    public Master findMasterById(int id) {
        try {
            return masterDao.getMasterById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public int importMaster(String fileName) {
        try {
            List<String> masterDataList = CsvUtil.importCsvFile(fileName);
            if (masterDataList == null) {
                throw new FileNotFoundException();
            }
            Master importMaster = new Master(Integer.parseInt(masterDataList.get(0)), masterDataList.get(1), Integer.parseInt(masterDataList.get(2)),
                    Boolean.parseBoolean(masterDataList.get(3)), orderDao.getOrderById(Integer.parseInt(masterDataList.get(4))));

            if (masterDao.getMasterById(importMaster.getId()) != null) {
                masterDao.updateMaster(importMaster);
                return 1;
            } else {
                return masterDao.addMaster(importMaster);
            }
        } catch (WrongFileFormatException e) {
            logger.error("Неверный формат файла");
        } catch (FileNotFoundException e) {
            logger.error("Файл не найден");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean exportMaster(int id, String fileName) {
        try {
            Master masterToExport = masterDao.getMasterById(id);
            if (masterToExport != null) {
                return CsvUtil.exportCsvFile(toList(masterToExport), fileName);
            } else {
                logger.error("Неверный № мастера");
            }
        } catch (WrongFileFormatException e) {
            logger.error("Неверный формат файла");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public List<String> toList(Master master) {
        List<String> masterAsList = new ArrayList<>();
        masterAsList.add(String.valueOf(master.getId()));
        masterAsList.add(master.getName());
        masterAsList.add(String.valueOf(master.getCategory()));
        masterAsList.add(String.valueOf(master.isBusy()));
        masterAsList.add(String.valueOf(master.getOrderId()));
        return masterAsList;
    }

    @Override
    public void saveState() {
        SerializeUtil.saveState(getAllMasters(), "SerialsMasters.out");
    }

    @Override
    public void loadState() {
        masterDao.setAllMasters(SerializeUtil.loadState(Master.class, "SerialsMasters.out"));
    }

    private Comparator getMasterComparator(String sortBy) {
        Comparator masterComparator = null;
        switch (sortBy) {
            case "byName":
                masterComparator = MasterByNameComparator.getInstance();
                break;
            case "byBusy":
                masterComparator = MasterByBusyComparator.getInstance();
                break;
        }
        return masterComparator;
    }

}

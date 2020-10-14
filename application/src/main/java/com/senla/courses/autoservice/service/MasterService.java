package com.senla.courses.autoservice.service;

import com.lib.utils.CsvUtil;
import com.lib.utils.exceptions.WrongFileFormatException;
import com.senla.courses.autoservice.dao.interfaces.IMasterDao;
import com.senla.courses.autoservice.dao.interfaces.IOrderDao;
import com.senla.courses.autoservice.dao.jpadao.DbJpaConnector;
import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.dto.mappers.MasterMapper;
import com.senla.courses.autoservice.dto.mappers.OrderMapper;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterAddingException;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterModifyingException;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterNotFoundException;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.service.comparators.master.MasterByBusyComparator;
import com.senla.courses.autoservice.service.comparators.master.MasterByNameComparator;
import com.senla.courses.autoservice.service.interfaces.IMasterService;
import com.senla.courses.autoservice.utils.SerializeUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityTransaction;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Getter
@Service
public class MasterService implements IMasterService {

    @Autowired
    private IMasterDao masterDao;
    @Autowired
    private IOrderDao orderDao;
    @Autowired
    DbJpaConnector dbJpaConnector;
    @Autowired
    private MasterMapper mapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    //@Transactional
    public void addMaster(MasterDto master) throws MasterAddingException {
        EntityTransaction transaction = dbJpaConnector.getTransaction();
        try {
            transaction.begin();
            masterDao.addMaster(mapper.masterDtoToMaster(master));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new MasterAddingException(e.getMessage());
        } finally {
            dbJpaConnector.closeSession();
        }
    }

    @Override
    public void removeMaster(String name) throws MasterModifyingException {
        EntityTransaction transaction = null;
        Master master = mapper.masterDtoToMaster(findMasterByName(name));
        try {
            transaction = dbJpaConnector.getTransaction();
            transaction.begin();
            masterDao.removeMaster(master);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new MasterModifyingException("Error deleting master");
        } finally {
            dbJpaConnector.closeSession();
        }
    }

    @Override
    public void updateMaster(MasterDto master) throws MasterModifyingException {
        EntityTransaction transaction = dbJpaConnector.getTransaction();
        try {
            transaction.begin();
            masterDao.updateMaster(mapper.masterDtoToMaster(master));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new MasterModifyingException("Error updating the master");
        } finally {
            dbJpaConnector.closeSession();
        }
    }

    private List<Master> getAllMasters() throws MasterNotFoundException {
        try {
            List<Master> masters = masterDao.getAllMasters();
            if (masters == null || masters.isEmpty()) {
                throw new MasterNotFoundException("Masters not found");
            }
            return masters;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MasterNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<MasterDto> getAllDtoMasters() throws MasterNotFoundException {
        return mapper.masterListToMasterDtoList(getAllMasters());
    }

    @Override
    public List<MasterDto> getAllMastersSorted(String sortBy) throws MasterNotFoundException {
        List<Master> allMastersSorted = new ArrayList<>();
        try {
            allMastersSorted.addAll(masterDao.getAllMasters());
            if (allMastersSorted == null || allMastersSorted.isEmpty()) {
                throw new MasterNotFoundException("Masters not found");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        Comparator masterComparator = getMasterComparator(sortBy);
        if (masterComparator != null) {
            allMastersSorted.sort(masterComparator);
        }
        return mapper.masterListToMasterDtoList(allMastersSorted);
    }

    @Override
    public List<MasterDto> getAllFreeMasters() throws MasterNotFoundException {
        try {
            List<Master> masters =  getAllMasters().stream()
                    .filter(master -> !master.isBusy())
                    .collect(Collectors.toList());
            return mapper.masterListToMasterDtoList(masters);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public MasterDto findMasterByName(String name) throws MasterNotFoundException {
        for (Master master : getAllMasters()) {
            if (master.getName().equals(name)) {
                return mapper.masterToMasterDto(master);
            }
        }
        throw new MasterNotFoundException("Master not found");
    }

    @Override
    public MasterDto findMasterById(int id) throws MasterNotFoundException {
        try {
            return mapper.masterToMasterDto(masterDao.getMasterById(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MasterNotFoundException(e.getMessage());
        }
    }

    @Override
    public void importMaster(String fileName) {
        try {
            List<String> masterDataList = CsvUtil.importCsvFile(fileName);
            if (masterDataList == null) {
                throw new FileNotFoundException();
            }
            Master importMaster = new Master(Integer.parseInt(masterDataList.get(0)), masterDataList.get(1), Integer.parseInt(masterDataList.get(2)),
                    Boolean.parseBoolean(masterDataList.get(3)), orderDao.getOrderById(Integer.parseInt(masterDataList.get(4))));

            if (masterDao.getMasterById(importMaster.getId()) != null) {
                masterDao.updateMaster(importMaster);
            } else {
                masterDao.addMaster(importMaster);
            }
        } catch (WrongFileFormatException e) {
            log.error("Неверный формат файла");
        } catch (FileNotFoundException e) {
            log.error("Файл не найден");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public boolean exportMaster(int id, String fileName) {
        try {
            Master masterToExport = masterDao.getMasterById(id);
            if (masterToExport != null) {
                return CsvUtil.exportCsvFile(toList(masterToExport), fileName);
            } else {
                log.error("Неверный № мастера");
            }
        } catch (WrongFileFormatException e) {
            log.error("Неверный формат файла");
        } catch (Exception e) {
            log.error(e.getMessage());
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
    public void saveState() throws MasterNotFoundException {
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

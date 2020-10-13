package com.senla.courses.autoservice.service;

import com.lib.utils.CsvUtil;
import com.lib.utils.exceptions.WrongFileFormatException;
import com.senla.courses.autoservice.dao.interfaces.IGarageDao;
import com.senla.courses.autoservice.dao.interfaces.IGaragePlaceDao;
import com.senla.courses.autoservice.dao.jpadao.DbJpaConnector;
import com.senla.courses.autoservice.dto.GarageDto;
import com.senla.courses.autoservice.dto.GaragePlaceDto;
import com.senla.courses.autoservice.dto.mappers.GarageMapper;
import com.senla.courses.autoservice.dto.mappers.GaragePlaceMapper;
import com.senla.courses.autoservice.exceptions.garageexceptions.GarageAddingException;
import com.senla.courses.autoservice.exceptions.garageexceptions.GarageNotFoundException;
import com.senla.courses.autoservice.exceptions.garageexceptions.GarageRemovingException;
import com.senla.courses.autoservice.model.Garage;
import com.senla.courses.autoservice.model.GaragePlace;
import com.senla.courses.autoservice.service.interfaces.IGarageService;
import com.senla.courses.autoservice.service.interfaces.IMasterService;
import com.senla.courses.autoservice.utils.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityTransaction;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class GarageService implements IGarageService {

    @Autowired
    private IGarageDao garageDao;
    @Autowired
    private IGaragePlaceDao garagePlaceDao;
    @Autowired
    private IMasterService masterService;
    @Autowired
    DbJpaConnector dbJpaConnector;
    @Autowired
    private GarageMapper garageMapper;
    @Autowired
    private GaragePlaceMapper garagePlaceMapper;
    @Value("${addGaragePlaceOption}")
    private boolean addGaragePlaceOption;
    @Value("${removeGaragePlaceOption}")
    private boolean removeGaragePlaceOption;

    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void addGarage(GarageDto garage) throws GarageAddingException {
        EntityTransaction transaction = dbJpaConnector.getTransaction();
        try {
            transaction.begin();
            garageDao.addGarage(garageMapper.garageDtoToGarage(garage));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new GarageAddingException(e.getMessage());
        } finally {
            dbJpaConnector.closeSession();
        }
    }

    @Override
    public void removeGarage(int garageId) throws GarageNotFoundException, GarageRemovingException {
        EntityTransaction transaction = null;
        Garage garage = garageMapper.garageDtoToGarage(findGarageById(garageId));
        if (garage == null) {
            log.error("Garage not found");
            throw new GarageNotFoundException("Garage not found");
        }
        try {
            transaction = dbJpaConnector.getTransaction();
            transaction.begin();
            List<GaragePlace> garagePlaces = garage.getGaragePlaces();
            for (GaragePlace garagePlace : garagePlaces) {
                garagePlaceDao.removeGaragePlace(garagePlace);
            }
            garageDao.removeGarage(garage);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            log.error(e.getMessage());
            throw new GarageRemovingException(e.getMessage());
        } finally {
            dbJpaConnector.closeSession();
        }
    }

    @Override
    public List<GarageDto> getAllGarages() throws GarageNotFoundException {
        List<GarageDto> garages = null;
        try {
            garages = garageMapper.garageListToGarageDtoList(garageDao.getAllGarages());
            if (garages == null || garages.isEmpty()) {
                throw new GarageNotFoundException("Garages not found");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GarageNotFoundException(ex.getMessage());
        }
        return garages;
    }

    @Override
    //@Transactional(transactionManager = "transactionManager")
    public void addGaragePlace(GaragePlaceDto garagePlace) throws GarageAddingException {
        if (addGaragePlaceOption) {
            EntityTransaction transaction = dbJpaConnector.getTransaction();
            try {
                transaction.begin();
                garagePlaceDao.addGaragePlace(garagePlaceMapper.garagePlaceDtoToGaragePlace(garagePlace));
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                log.error(e.getMessage());
                throw new GarageAddingException(e.getMessage());
            } finally {
                dbJpaConnector.closeSession();
            }
        } else {
            log.warn("Возможность добавления места в гараже отключена");
        }
    }

    @Override
    public void removeGaragePlace(int garageId, int garagePlaceId) throws GarageNotFoundException, GarageRemovingException {
        if (removeGaragePlaceOption) {
            EntityTransaction transaction = null;
            GaragePlace garagePlace = garagePlaceMapper.garagePlaceDtoToGaragePlace(findGaragePlaceById(garageId, garagePlaceId));
            if (garagePlace == null) {
                log.error("Garage place not found");
                throw new GarageNotFoundException("Garage place not found");
            }
            try {
                transaction = dbJpaConnector.getTransaction();
                transaction.begin();
                garagePlaceDao.removeGaragePlace(garagePlace);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                log.error(e.getMessage());
                throw  new GarageRemovingException(e.getMessage());
            } finally {
                dbJpaConnector.closeSession();
            }
        } else {
            log.warn("Возможность удаления места в гараже отключена");
        }
    }

    @Override
    public List<GaragePlaceDto> getAllFreePlaces() {
        List<GaragePlace> freePlaces = new ArrayList<>();
        try {
            garageDao.getAllGarages().stream()
                    .forEach(garage -> garage.getGaragePlaces().stream()
                            .filter(garagePlace -> !garagePlace.isBusy())
                            .forEach(garagePlace -> freePlaces.add(garagePlace)));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return garagePlaceMapper.garagePlaceListToGaragePlaceDtoList(freePlaces);
    }

    @Override
    public int getFreePlacesCountInFuture() {
        return Math.min(getAllFreePlaces().size(), masterService.getAllFreeMasters().size());
    }

    @Override
    public GaragePlaceDto findGaragePlaceById(int garageId, int garagePlaceId) {
        GaragePlace garagePlace = null;
        try {
            garagePlace = garagePlaceDao.getGaragePlaceById(garageId, garagePlaceId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return garagePlaceMapper.garagePlaceToGaragePlaceDto(garagePlace);
    }

    @Override
    public GarageDto findGarageById(int garageId) {
        Garage garage = null;
        try {
            garage = garageDao.getGarageById(garageId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return garageMapper.garageToGarageDto(garage);
    }

    @Override
    public void importGarage(String fileName) {
        Garage importGarage;
        try {
            List<String> garageDataList = CsvUtil.importCsvFile(fileName);
            if (garageDataList == null) {
                throw new FileNotFoundException();
            }
            if (garageDataList.get(2).equals("null")) {
                importGarage = new Garage(Integer.parseInt(garageDataList.get(0)), garageDataList.get(1), null);
            } else {
                List<GaragePlace> importGaragePlaces = new ArrayList<>();
                for (int i = 0; i < (garageDataList.size() - 2) / 5; i++) {
                    GaragePlace importGaragePlace = new GaragePlace(Integer.parseInt(garageDataList.get(2 + i * 5)),
                            garageMapper.garageDtoToGarage(findGarageById(Integer.parseInt(garageDataList.get(3 + i * 5)))),
                            garageDataList.get(4 + i * 5), Integer.parseInt(garageDataList.get(5 + i * 5)), Boolean.parseBoolean(garageDataList.get(6 + i * 5)));
                    importGaragePlaces.add(importGaragePlace);
                }
                importGarage = new Garage(Integer.parseInt(garageDataList.get(0)), garageDataList.get(1), importGaragePlaces);
            }

            if (findGarageById(importGarage.getId()) != null) {
                garageDao.updateGarage(importGarage);
            } else {
                garageDao.addGarage(importGarage);
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
    public boolean exportGarage(int id, String fileName) {
        try {
            GarageDto garageToExport = findGarageById(id);
            if (garageToExport != null) {
                return CsvUtil.exportCsvFile(garageToList(garageMapper.garageDtoToGarage(garageToExport)), fileName);
            } else {
                log.error("Неверный № гаража");
            }
        } catch (WrongFileFormatException e) {
            log.error("Неверный формат файла");
        }
        return false;
    }

    @Override
    public void importGaragePlace(String fileName) {
        try {
            List<String> garagePlaceDataList = CsvUtil.importCsvFile(fileName);
            if (garagePlaceDataList == null) {
                throw new FileNotFoundException();
            }
            GaragePlace importGaragePlace = new GaragePlace(Integer.parseInt(garagePlaceDataList.get(0)), garageMapper.garageDtoToGarage(findGarageById(Integer.parseInt(garagePlaceDataList.get(1)))),
                    garagePlaceDataList.get(2), Integer.parseInt(garagePlaceDataList.get(3)), Boolean.parseBoolean(garagePlaceDataList.get(4)));

            if (garagePlaceDao.getGaragePlaceById(importGaragePlace.getGarageId(), importGaragePlace.getId()) != null) {
                garagePlaceDao.updateGaragePlace(importGaragePlace);
            } else {
                garagePlaceDao.addGaragePlace(importGaragePlace);
            }
        } catch (WrongFileFormatException e) {
            log.error("Неверный формат файла");
        } catch (FileNotFoundException e) {
            log.error("Файл не найден");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public boolean exportGaragePlace(int garageId, int garagePlaceId, String fileName) {
        try {
            GaragePlace garagePlaceToExport = garagePlaceMapper.garagePlaceDtoToGaragePlace(findGaragePlaceById(garageId, garagePlaceId));
            if (garagePlaceToExport != null) {
                return CsvUtil.exportCsvFile(garagePlaceToList(garagePlaceToExport), fileName);
            } else {
                log.error("Неверный № гаража или места в гараже");
                return false;
            }
        } catch (WrongFileFormatException e) {
            log.error("Неверный формат файла");
        }
        return false;
}

    @Override
    public List<String> garageToList(Garage garage) {
        List<String> garageAsList = new ArrayList<>();
        garageAsList.add(String.valueOf(garage.getId()));
        garageAsList.add(garage.getAddress());
        if (garage.getGaragePlaces().size() != 0) {
            garage.getGaragePlaces().stream()
                    .forEach(garagePlace -> garageAsList.addAll(garagePlaceToList(garagePlace)));
        } else {
            garageAsList.add("null");
        }
        return garageAsList;
    }

    @Override
    public List<String> garagePlaceToList(GaragePlace garagePlace) {
        List<String> garagePlaceAsList = new ArrayList<>();
        garagePlaceAsList.add(String.valueOf(garagePlace.getId()));
        garagePlaceAsList.add(String.valueOf(garagePlace.getGarageId()));
        garagePlaceAsList.add(garagePlace.getType());
        garagePlaceAsList.add(String.valueOf(garagePlace.getArea()));
        garagePlaceAsList.add(String.valueOf(garagePlace.isBusy()));
        return garagePlaceAsList;
    }

    @Override
    public void saveState() {
        SerializeUtil.saveState(getAllGarages(), "SerialsGarages.out");
    }

    @Override
    public void loadState() {
        garageDao.setAllGarages(SerializeUtil.loadState(Garage.class, "SerialsGarages.out"));
    }

}

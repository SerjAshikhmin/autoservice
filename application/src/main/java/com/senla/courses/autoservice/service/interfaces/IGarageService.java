package com.senla.courses.autoservice.service.interfaces;

import com.senla.courses.autoservice.dto.GarageDto;
import com.senla.courses.autoservice.dto.GaragePlaceDto;
import com.senla.courses.autoservice.exceptions.garageexceptions.GarageAddingException;
import com.senla.courses.autoservice.exceptions.garageexceptions.GarageNotFoundException;
import com.senla.courses.autoservice.exceptions.garageexceptions.GarageRemovingException;
import com.senla.courses.autoservice.model.Garage;
import com.senla.courses.autoservice.model.GaragePlace;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IGarageService {

    void addGarage(GarageDto garage) throws GarageAddingException;
    void removeGarage(int garageId) throws GarageNotFoundException, GarageRemovingException;
    List<GarageDto> getAllGarages() throws GarageNotFoundException;
    void addGaragePlace(GaragePlaceDto garagePlace) throws GarageAddingException;
    void removeGaragePlace(int garageId, int garagePlaceId) throws GarageNotFoundException, GarageRemovingException;
    List<GaragePlaceDto> getAllFreePlaces() throws GarageNotFoundException ;
    int getFreePlacesCountInFuture();
    GaragePlaceDto findGaragePlaceById(int garageId, int garagePlaceId);
    GarageDto findGarageById(int id);
    void importGarage(String fileName);
    boolean exportGarage(int id, String fileName);
    void importGaragePlace(String fileName);
    boolean exportGaragePlace(int garageId, int garagePlaceId, String fileName);
    List<String> garageToList(Garage garage);
    List<String> garagePlaceToList(GaragePlace garagePlace);
    void saveState();
    void loadState();
}

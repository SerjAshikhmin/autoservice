package com.senla.courses.autoservice.service.interfaces;

import com.senla.courses.autoservice.model.Garage;
import com.senla.courses.autoservice.model.GaragePlace;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface IGarageService {

    int addGarage(Garage garage);
    int removeGarage(int garageId);
    List<Garage> getAllGarages();
    int addGaragePlace(GaragePlace garagePlace);
    int removeGaragePlace(int garageId, int garagePlaceId);
    List<GaragePlace> getAllFreePlaces();
    int getFreePlacesCountInFuture();
    GaragePlace findGaragePlaceById(int garageId, int garagePlaceId);
    Garage findGarageById(int id);
    int importGarage(String fileName);
    boolean exportGarage(int id, String fileName);
    int importGaragePlace(String fileName);
    boolean exportGaragePlace(int garageId, int garagePlaceId, String fileName);
    List<String> garageToList(Garage garage);
    List<String> garagePlaceToList(GaragePlace garagePlace);
    void saveState();
    void loadState();
}

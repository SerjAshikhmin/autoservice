package com.senla.courses.autoservice.controller;

import com.senla.courses.autoservice.dto.GarageDto;
import com.senla.courses.autoservice.dto.GaragePlaceDto;
import com.senla.courses.autoservice.dto.mappers.GarageMapper;
import com.senla.courses.autoservice.dto.mappers.GaragePlaceMapper;
import com.senla.courses.autoservice.service.interfaces.IGarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garages")
public class GarageController {

    @Autowired
    private IGarageService garageService;
    @Autowired
    private GarageMapper garageMapper;
    @Autowired
    private GaragePlaceMapper garagePlaceMapper;

    @GetMapping("")
    public ResponseEntity<List<GarageDto>> getAllGarages() {
        final List<GarageDto> garages = garageMapper.garageListToGarageDtoList(garageService.getAllGarages());
        return garages != null && !garages.isEmpty()
                ? new ResponseEntity<>(garages, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<?> addGarage(@RequestBody GarageDto garage) {
        return garageService.addGarage(garageMapper.garageDtoToGarage(garage)) == 1
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeGarage(@PathVariable("id") int id) {
        return garageService.removeGarage(id) == 1
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PostMapping("/garagePlace")
    public ResponseEntity<?> addGaragePlace(@RequestBody GaragePlaceDto garagePlace) {
        return garageService.addGaragePlace(garagePlaceMapper.garagePlaceDtoToGaragePlace(garagePlace)) == 1
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{garageId}/{garagePlaceId}")
    public ResponseEntity<?> removeGaragePlace(@PathVariable("garageId") int garageId, @PathVariable("garagePlaceId") int garagePlaceId) {
        return garageService.removeGaragePlace(garageId, garagePlaceId) == 1
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/freePlaces")
    public ResponseEntity<List<GaragePlaceDto>> getAllFreePlaces() {
        final List<GaragePlaceDto> garagePlaces = garagePlaceMapper.garagePlaceListToGaragePlaceDtoList(garageService.getAllFreePlaces());
        return garagePlaces != null && !garagePlaces.isEmpty()
                ? new ResponseEntity<>(garagePlaces, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/freePlacesCountInFuture")
    public ResponseEntity<Integer> getFreePlacesCountInFuture() {
        return new ResponseEntity<>(garageService.getFreePlacesCountInFuture(), HttpStatus.OK);
    }

    public int importGarage(String fileName) {
        return garageService.importGarage(fileName);
    }

    public boolean exportGarage(int id, String fileName) {
        return garageService.exportGarage(id, fileName);
    }

    public int importGaragePlace(String fileName) {
        return garageService.importGaragePlace(fileName);
    }

    public boolean exportGaragePlace(int garageId, int garagePlaceId, String fileName) {
        return garageService.exportGaragePlace(garageId, garagePlaceId, fileName);
    }

    public void saveState() {
        garageService.saveState();
    }

    public void loadState() {
        garageService.loadState();
    }
}

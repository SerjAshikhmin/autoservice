package com.senla.courses.autoservice.controller;

import com.senla.courses.autoservice.dto.GarageDto;
import com.senla.courses.autoservice.dto.GaragePlaceDto;
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

    @GetMapping("")
    public ResponseEntity<List<GarageDto>> getAllGarages() {
        final List<GarageDto> garages = garageService.getAllGarages();
        return ResponseEntity.ok(garages);
    }

    @PostMapping("")
    public ResponseEntity<?> addGarage(@RequestBody GarageDto garage) {
        garageService.addGarage(garage);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeGarage(@PathVariable("id") int id) {
        garageService.removeGarage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/garagePlaces")
    public ResponseEntity<?> addGaragePlace(@RequestBody GaragePlaceDto garagePlace) {
        garageService.addGaragePlace(garagePlace);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{garageId}/garagePlaces/{garagePlaceId}")
    public ResponseEntity<?> removeGaragePlace(@PathVariable("garageId") int garageId, @PathVariable("garagePlaceId") int garagePlaceId) {
        garageService.removeGaragePlace(garageId, garagePlaceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/freePlaces")
    public ResponseEntity<List<GaragePlaceDto>> getAllFreePlaces() {
        final List<GaragePlaceDto> garagePlaces = garageService.getAllFreePlaces();
        return ResponseEntity.ok(garagePlaces);
    }

    @GetMapping("/freePlacesCountInFuture")
    public ResponseEntity<Integer> getFreePlacesCountInFuture() {
        return new ResponseEntity<>(garageService.getFreePlacesCountInFuture(), HttpStatus.OK);
    }

    public void importGarage(String fileName) {
        garageService.importGarage(fileName);
    }

    public boolean exportGarage(int id, String fileName) {
        return garageService.exportGarage(id, fileName);
    }

    public void importGaragePlace(String fileName) {
        garageService.importGaragePlace(fileName);
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

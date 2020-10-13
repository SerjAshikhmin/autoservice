package com.senla.courses.autoservice.controller;

import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.service.interfaces.IMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/masters")
public class MasterController {

    @Autowired
    private IMasterService masterService;

    @GetMapping("")
    public ResponseEntity<List<MasterDto>> getAllMasters() {
        final List<MasterDto> masters = masterService.getAllDtoMasters();
        return ResponseEntity.ok(masters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasterDto> findMasterById(@PathVariable(name = "id") int id) {
        final MasterDto master = masterService.findMasterById(id);
        return ResponseEntity.ok(master);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<MasterDto>> getAllMastersSorted(@RequestParam("sortBy") String sortBy) {
        final List<MasterDto> masters = masterService.getAllMastersSorted(sortBy);
        return ResponseEntity.ok(masters);
    }

    @PostMapping("")
    public ResponseEntity<?> addMaster(@Validated MasterDto master) {
        masterService.addMaster(master);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<?> updateMaster(@Validated MasterDto master) {
        masterService.updateMaster(master);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> removeMaster(@PathVariable("name") String name) {
        masterService.removeMaster(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/orders/{name}")
    public ResponseEntity<?> getCurrentOrder(@PathVariable("name") String name) {
        OrderDto order = masterService.getCurrentOrder(name);
        return ResponseEntity.ok(order);
    }

    public void importMaster(String fileName) {
        masterService.importMaster(fileName);
    }

    public boolean exportMaster(int id, String fileName) {
        return masterService.exportMaster(id, fileName);
    }

    public void saveState() {
        masterService.saveState();
    }

    public void loadState() {
        masterService.loadState();
    }
}

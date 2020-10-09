package com.senla.courses.autoservice.controller;

import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.dto.mappers.MasterMapper;
import com.senla.courses.autoservice.dto.mappers.OrderMapper;
import com.senla.courses.autoservice.service.interfaces.IMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/masters")
public class MasterController {

    @Autowired
    private IMasterService masterService;
    @Autowired
    private MasterMapper mapper;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("")
    public ResponseEntity<List<MasterDto>> getAllMasters() {
        final List<MasterDto> masters = mapper.masterListToMasterDtoList(masterService.getAllMasters());
        return masters != null &&  !masters.isEmpty()
                ? new ResponseEntity<>(masters, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasterDto> findMasterById(@PathVariable(name = "id") int id) {
        final MasterDto master = mapper.masterToMasterDto(masterService.findMasterById(id));
        return master != null
                ? new ResponseEntity<>(master, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<MasterDto>> getAllMastersSorted(@RequestParam("sortBy") String sortBy) {
        final List<MasterDto> masters = mapper.masterListToMasterDtoList(masterService.getAllMastersSorted(sortBy));
        return masters != null && !masters.isEmpty()
                ? new ResponseEntity<>(masters, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<?> addMaster(@RequestBody MasterDto master) {
        return masterService.addMaster(mapper.masterDtoToMaster(master)) == 1
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("")
    public ResponseEntity<?> updateMaster(@RequestBody MasterDto master) {
        return masterService.updateMaster(mapper.masterDtoToMaster(master)) == 1
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> removeMaster(@PathVariable("name") String name) {
        return masterService.removeMaster(name) == 1
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/order/{name}")
    public ResponseEntity<?> getCurrentOrder(@PathVariable("name") String name) {
        OrderDto order = orderMapper.orderToOrderDto(masterService.getCurrentOrder(name));
        return order != null
                ? new ResponseEntity<>(order, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public int importMaster(String fileName) {
        return masterService.importMaster(fileName);
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

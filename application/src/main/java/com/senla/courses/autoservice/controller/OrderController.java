package com.senla.courses.autoservice.controller;

import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.dto.validators.OrderDtoValidator;
import com.senla.courses.autoservice.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private OrderDtoValidator orderDtoValidator;

    @GetMapping("")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        final List<OrderDto> orders = orderService.getAllDtoOrders();
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable("id") int id) {
        final OrderDto order = orderService.findOrderById(id);
        return order != null
                ? new ResponseEntity<>(order, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
                             produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addOrder(@RequestBody OrderDto order, BindingResult result) {
        orderDtoValidator.validate(order, result);
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderService.addOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeOrder(@PathVariable("id") int id) {
        orderService.removeOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("id") int id) {
        orderService.cancelOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/close/{id}")
    public ResponseEntity<?> closeOrder(@PathVariable("id") int id) {
        orderService.closeOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/shiftEndTimeOrders")
    public ResponseEntity<?> shiftEndTimeOrders(@RequestParam("hours") int hours, @RequestParam("minutes") int minutes) {
        orderService.shiftEndTimeOrders(hours, minutes);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<OrderDto>> getAllOrdersSorted(@RequestParam("sortBy") String sortBy) {
        final List<OrderDto> orders = orderService.getAllOrdersSorted(sortBy);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/inProgress")
    public ResponseEntity<List<OrderDto>> getAllOrdersInProgress(@RequestParam("sortBy") String sortBy) {
        final List<OrderDto> orders = orderService.getAllOrdersInProgress(sortBy);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/masters/{id}")
    public ResponseEntity<List<MasterDto>> getMastersByOrder(@PathVariable("id") int id) {
        final List<MasterDto> masters = orderService.getMastersByOrder(id);
        return masters != null && !masters.isEmpty()
                ? new ResponseEntity<>(masters, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/ordersByPeriod")
    public ResponseEntity<List<OrderDto>> getOrdersByPeriod (@RequestParam("startPeriod") String startPeriodStr,
                                                             @RequestParam("endPeriod") String endPeriodStr, @RequestParam("sortBy") String sortBy) {
        LocalDateTime startPeriod = LocalDateTime.parse(startPeriodStr);
        LocalDateTime endPeriod = LocalDateTime.parse(endPeriodStr);
        final List<OrderDto> orders = orderService.getOrdersByPeriod(startPeriod, endPeriod, sortBy);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/nearestDate")
    public ResponseEntity<LocalDateTime> getNearestFreeDate() {
        LocalDateTime nearestFreeDate = orderService.getNearestFreeDate();
        return nearestFreeDate != null
                ? new ResponseEntity<>(nearestFreeDate, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void importOrder(String fileName) {
        orderService.importOrder(fileName);
    }

    public boolean exportOrder(int id, String fileName) {
        return orderService.exportOrder(id, fileName);
    }

    public void saveState() {
        orderService.saveState();
    }

    public void loadState() {
        orderService.loadState();
    }
}

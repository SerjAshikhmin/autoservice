package com.senla.courses.autoservice.controller;

import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.dto.mappers.MasterMapper;
import com.senla.courses.autoservice.dto.mappers.OrderMapper;
import com.senla.courses.autoservice.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private MasterMapper masterMapper;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        final List<OrderDto> orders = orderMapper.orderListToOrderDtoList(orderService.getAllOrders());
        return orders != null &&  !orders.isEmpty()
                ? new ResponseEntity<>(orders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable("id") int id) {
        final OrderDto order = orderMapper.orderToOrderDto(orderService.findOrderById(id));
        return order != null
                ? new ResponseEntity<>(order, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
                             produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addOrder(@RequestBody OrderDto order) {
        return orderService.addOrder(orderMapper.orderDtoToOrder(order)) == 1
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeOrder(@PathVariable("id") int id) {
        return orderService.removeOrder(id) == 1
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("id") int id) {
        return orderService.cancelOrder(id) == 1
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/close/{id}")
    public ResponseEntity<?> closeOrder(@PathVariable("id") int id) {
        return orderService.closeOrder(id) == 1
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/shiftEndTimeOrders")
    public ResponseEntity<?> shiftEndTimeOrders(@RequestParam("hours") int hours, @RequestParam("minutes") int minutes) {
        orderService.shiftEndTimeOrders(hours, minutes);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<OrderDto>> getAllOrdersSorted(@RequestParam("sortBy") String sortBy) {
        final List<OrderDto> orders = orderMapper.orderListToOrderDtoList(orderService.getAllOrdersSorted(sortBy));
        return orders != null && !orders.isEmpty()
                ? new ResponseEntity<>(orders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/inProgress")
    public ResponseEntity<List<OrderDto>> getAllOrdersInProgress(@RequestParam("sortBy") String sortBy) {
        final List<OrderDto> orders = orderMapper.orderListToOrderDtoList(orderService.getAllOrdersInProgress(sortBy));
        return orders != null && !orders.isEmpty()
                ? new ResponseEntity<>(orders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/masters/{id}")
    public ResponseEntity<List<MasterDto>> getMastersByOrder(@PathVariable("id") int id) {
        final List<MasterDto> masters = masterMapper.masterListToMasterDtoList(orderService.getMastersByOrder(id));
        return masters != null && !masters.isEmpty()
                ? new ResponseEntity<>(masters, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/ordersByPeriod")
    public ResponseEntity<List<OrderDto>> getOrdersByPeriod (@RequestParam("startPeriod") String startPeriodStr,
                                                             @RequestParam("endPeriod") String endPeriodStr, @RequestParam("sortBy") String sortBy) {
        LocalDateTime startPeriod = LocalDateTime.parse(startPeriodStr);
        LocalDateTime endPeriod = LocalDateTime.parse(endPeriodStr);
        final List<OrderDto> orders = orderMapper.orderListToOrderDtoList(orderService.getOrdersByPeriod(startPeriod, endPeriod, sortBy));
        return orders != null && !orders.isEmpty()
                ? new ResponseEntity<>(orders, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/nearestDate")
    public ResponseEntity<LocalDateTime> getNearestFreeDate() {
        LocalDateTime nearestFreeDate = orderService.getNearestFreeDate();
        return nearestFreeDate != null
                ? new ResponseEntity<>(nearestFreeDate, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public int importOrder(String fileName) {
        return orderService.importOrder(fileName);
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

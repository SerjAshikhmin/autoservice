package com.senla.courses.autoservice.service.interfaces;

import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterNotFoundException;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderAddingException;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderModifyingException;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderNotFoundException;
import com.senla.courses.autoservice.model.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public interface IOrderService {

    void addOrder(OrderDto order) throws MasterNotFoundException, OrderAddingException;
    void removeOrder(int id) throws OrderNotFoundException, OrderModifyingException;
    void cancelOrder(int id) throws OrderNotFoundException, OrderModifyingException;
    void closeOrder(int id) throws OrderNotFoundException, OrderModifyingException;
    List<OrderDto> getAllDtoOrders() throws OrderNotFoundException;
    List<OrderDto> getAllOrdersSorted(String sortBy) throws OrderNotFoundException ;
    List<OrderDto> getAllOrdersInProgress(String sortBy) throws OrderNotFoundException ;
    LocalDateTime getNearestFreeDate();
    List<MasterDto> getMastersByOrder(int id);
    List<OrderDto> getOrdersByPeriod(LocalDateTime startPeriod, LocalDateTime endPeriod, String sortBy) throws OrderNotFoundException ;
    void updateOrderTime(OrderDto order, LocalDateTime newStartTime, LocalDateTime newEndTime) throws OrderModifyingException;
    void shiftEndTimeOrders(int hours, int minutes);
    OrderDto findOrderById(int id) throws OrderNotFoundException;
    void importOrder(String fileName);
    boolean exportOrder(int id, String fileName);
    List<String> toList(Order order);
    void saveState();
    void loadState();
}

package com.senla.courses.autoservice.service;

import com.lib.utils.CsvUtil;
import com.lib.utils.exceptions.WrongFileFormatException;
import com.senla.courses.autoservice.dao.interfaces.IGaragePlaceDao;
import com.senla.courses.autoservice.dao.interfaces.IMasterDao;
import com.senla.courses.autoservice.dao.interfaces.IOrderDao;
import com.senla.courses.autoservice.dao.jpadao.DbJpaConnector;
import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.dto.mappers.GaragePlaceMapper;
import com.senla.courses.autoservice.dto.mappers.MasterMapper;
import com.senla.courses.autoservice.dto.mappers.OrderMapper;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterAddingException;
import com.senla.courses.autoservice.exceptions.masterexceptions.MasterNotFoundException;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderAddingException;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderModifyingException;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderNotFoundException;
import com.senla.courses.autoservice.model.GaragePlace;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import com.senla.courses.autoservice.model.enums.OrderStatus;
import com.senla.courses.autoservice.service.comparators.order.OrderByCostComparator;
import com.senla.courses.autoservice.service.comparators.order.OrderByEndDateComparator;
import com.senla.courses.autoservice.service.comparators.order.OrderBySubmissionDateComparator;
import com.senla.courses.autoservice.service.comparators.order.OrderByStartDateComparator;
import com.senla.courses.autoservice.service.interfaces.IGarageService;
import com.senla.courses.autoservice.service.interfaces.IMasterService;
import com.senla.courses.autoservice.service.interfaces.IOrderService;
import com.senla.courses.autoservice.utils.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IMasterDao masterDao;
    @Autowired
    private IGaragePlaceDao garagePlaceDao;
    @Autowired
    private IMasterService masterService;
    @Autowired
    private IGarageService garageService;
    @Autowired
    DbJpaConnector dbJpaConnector;
    @Autowired
    private MasterMapper masterMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GaragePlaceMapper garagePlaceMapper;
    @Value("${shiftEndTimeOrdersOption}")
    private boolean shiftEndTimeOrdersOption;
    @Value("${removeOrderOption}")
    private boolean removeOrderOption;

    @Override
    //@Transactional
    public void addOrder(OrderDto order) throws MasterNotFoundException, OrderAddingException {
        List<MasterDto> masters = new ArrayList<>();
        MasterDto master = order.getMasters().get(0);
        if (master == null) {
            log.error(String.format("Не найден мастер для заказа №%d", order.getId()));
            throw new MasterNotFoundException("Master not found");
        }
        master.setBusy(true);
        order.getGaragePlace().setBusy(true);
        EntityTransaction transaction = dbJpaConnector.getTransaction();
        try {
            transaction.begin();
            orderDao.addOrder(orderMapper.orderDtoToOrder(order));
            masterDao.updateMaster(masterMapper.masterDtoToMaster(master));
            garagePlaceDao.updateGaragePlace(garagePlaceMapper.garagePlaceDtoToGaragePlace(order.getGaragePlace()));
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            log.error(ex.getMessage());
            throw new MasterAddingException(ex.getMessage());
        } finally {
            dbJpaConnector.closeSession();
        }
    }

    @Override
    public void removeOrder(int id) throws OrderNotFoundException, OrderModifyingException {
        if (removeOrderOption) {
            Order order = orderMapper.orderDtoToOrder(findOrderById(id));
            if (order == null) {
                log.error("Order not found");
                throw new OrderNotFoundException("Order not found");
            }
            GaragePlace garagePlace = order.getGaragePlace();
            garagePlace.setBusy(false);
            garagePlace.setOrder(null);
            order.getMasters().stream()
                    .forEach(master -> {
                        master.setBusy(false);
                        master.setOrder(null);
                    });
            EntityTransaction transaction = dbJpaConnector.getTransaction();
            try {
                transaction.begin();
                for (Master master : order.getMasters()) {
                    masterDao.updateMaster(master);
                }
                garagePlaceDao.updateGaragePlace(order.getGaragePlace());
                orderDao.removeOrder(order);
                transaction.commit();
            } catch (PersistenceException ex) {
                transaction.rollback();
                log.error(ex.getMessage());
                throw new OrderModifyingException(ex.getMessage());
            } finally {
                dbJpaConnector.closeSession();
            }
        } else {
            log.warn("Возможность удаления заказов отключена");
        }
    }

    @Override
    public void cancelOrder(int id) throws OrderNotFoundException, OrderModifyingException {
        Order order = orderMapper.orderDtoToOrder(findOrderById(id));
        if (order != null) {
            order.getGaragePlace().setBusy(false);
            order.getMasters().stream()
                    .forEach(master -> master.setBusy(false));
            order.setStatus(OrderStatus.CANCELED);
            EntityTransaction transaction = dbJpaConnector.getTransaction();
            try {
                transaction.begin();
                orderDao.updateOrder(order);
                transaction.commit();
                log.info(String.format("Заказ №%d отменен", id));
            } catch (Exception ex) {
                transaction.rollback();
                log.error(ex.getMessage());
                throw new OrderModifyingException(ex.getMessage());
            } finally {
                dbJpaConnector.closeSession();
            }
        } else {
            log.error("Order not found");
            throw new OrderNotFoundException("Order not found");
        }
    }

    @Override
    public void closeOrder(int id) throws OrderNotFoundException, OrderModifyingException {
        Order order = orderMapper.orderDtoToOrder(findOrderById(id));
        if (order != null) {
            order.getGaragePlace().setBusy(false);
            order.getMasters().stream()
                    .forEach(master -> master.setBusy(false));
            order.setEndDate(LocalDateTime.now());
            order.setStatus(OrderStatus.COMPLETED);
            orderDao.updateOrder(order);
            EntityTransaction transaction = dbJpaConnector.getTransaction();
            try {
                transaction.begin();
                orderDao.updateOrder(order);
                transaction.commit();
                log.info(String.format("Заказ №%d закрыт", id));
            } catch (Exception ex) {
                transaction.rollback();
                log.error(ex.getMessage());
                throw new OrderModifyingException(ex.getMessage());
            } finally {
                dbJpaConnector.closeSession();
            }
        } else {
            log.error("Order not found");
            throw new OrderNotFoundException("Order not found");
        }
    }

    private List<Order> getAllOrders() throws OrderNotFoundException {
        List<Order> orders = null;
        try {
            orders = orderDao.getAllOrders();
            if (orders == null ||  orders.isEmpty()) {
                throw new OrderNotFoundException("Orders not found");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return orders;
    }

    @Override
    public List<OrderDto> getAllDtoOrders() throws OrderNotFoundException {
        return orderMapper.orderListToOrderDtoList(getAllOrders());
    }

    @Override
    public List<OrderDto> getAllOrdersSorted(String sortBy) throws OrderNotFoundException {
        List<Order> allOrdersSorted = new ArrayList<>();
        allOrdersSorted.addAll(getAllOrders());
        Comparator orderComparator = getOrderComparator(sortBy);
        if (orderComparator != null) {
            allOrdersSorted.sort(orderComparator);
        }
        return orderMapper.orderListToOrderDtoList(allOrdersSorted);
    }

    @Override
    public List<OrderDto> getAllOrdersInProgress(String sortBy) throws OrderNotFoundException {
        Comparator orderComparator = getOrderComparator(sortBy);
        List<Order> orders = null;
        try {
            orders = getAllOrders().stream()
                    .filter(order -> order.getStatus() == OrderStatus.IN_WORK)
                    .collect(Collectors.toList());

            if (orderComparator != null) {
                orders.sort(orderComparator);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return orderMapper.orderListToOrderDtoList(orders);
    }

    @Override
    public LocalDateTime getNearestFreeDate() {
        List<Order> allOrders = getAllOrders();
        final LocalDateTime nearestFreeDate = allOrders.get(0).getEndDate();
        return allOrders.stream()
                .filter(order -> order.getEndDate().compareTo(nearestFreeDate) == -1)
                .findFirst().get().getEndDate();
    }

    @Override
    public List<MasterDto> getMastersByOrder (int id) {
        Order order = orderMapper.orderDtoToOrder(findOrderById(id));
        try {
            return masterMapper.masterListToMasterDtoList(orderDao.getMastersByOrder(order));
        } catch (OrderNotFoundException e) {
            log.error("Неправильный номер заказа");
            return null;
        }
    }

    @Override
    public List<OrderDto> getOrdersByPeriod (LocalDateTime startPeriod, LocalDateTime endPeriod, String sortBy) throws OrderNotFoundException {
        List<Order> ordersByPeriod = new ArrayList<>();
        getAllOrders().stream()
                .filter(order -> startPeriod.compareTo(order.getEndDate()) <= -1 && endPeriod.compareTo(order.getEndDate()) >= 1)
                .forEach(order -> ordersByPeriod.add(order));

        Comparator orderComparator = getOrderComparator(sortBy);
        if (orderComparator != null) {
            ordersByPeriod.sort(orderComparator);
        }
        return orderMapper.orderListToOrderDtoList(ordersByPeriod);
    }

    @Override
    public void updateOrderTime(OrderDto order, LocalDateTime newStartTime, LocalDateTime newEndTime) throws OrderModifyingException {
        EntityTransaction transaction = dbJpaConnector.getTransaction();
        order.setStartDate(newStartTime);
        order.setEndDate(newEndTime);
        try {
            transaction.begin();
            orderDao.updateOrder(orderMapper.orderDtoToOrder(order));
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            log.error(ex.getMessage());
            throw new OrderModifyingException(ex.getMessage());
        } finally {
            dbJpaConnector.closeSession();
        }
    }

    @Override
    public void shiftEndTimeOrders(int hours, int minutes) {
        if (shiftEndTimeOrdersOption) {
            List<Order> allOrders = getAllOrders();
            allOrders.stream().forEach(order -> {
                updateOrderTime(orderMapper.orderToOrderDto(order), order.getStartDate(), order.getEndDate().plusHours(hours).plusMinutes(minutes));
            });
        } else {
            log.warn("Возможность смещать время выполнения заказов отключена");
        }
    }

    @Override
    public OrderDto findOrderById(int id) throws OrderNotFoundException {
        for (Order order : getAllOrders()) {
            if (order.getId() == id) {
                return orderMapper.orderToOrderDto(order);
            }
        }
        throw new OrderNotFoundException("Order not found");
    }

    @Override
    public void importOrder(String fileName) {
        try {
            List<String> orderDataList = CsvUtil.importCsvFile(fileName);
            if (orderDataList == null) {
                throw new FileNotFoundException();
            }
            Order importOrder;
            GaragePlace importGaragePlace = garagePlaceMapper.garagePlaceDtoToGaragePlace(garageService.findGaragePlaceById(Integer.parseInt(orderDataList.get(7)), Integer.parseInt(orderDataList.get(8))));
            List<Master> importMasters = new ArrayList<>();

            for (int i = 9; i < orderDataList.size(); i++) {
                importMasters.add(masterMapper.masterDtoToMaster(masterService.findMasterById(Integer.parseInt(orderDataList.get(i)))));
            }
            importOrder = new Order(Integer.parseInt(orderDataList.get(0)), LocalDateTime.parse(orderDataList.get(1)),
                    LocalDateTime.parse(orderDataList.get(2)), LocalDateTime.parse(orderDataList.get(3)), orderDataList.get(4),
                    Integer.parseInt(orderDataList.get(5)), importGaragePlace, importMasters, OrderStatus.valueOf(orderDataList.get(6)));

            if (orderDao.getOrderById(importOrder.getId()) != null) {
                orderDao.updateOrder(importOrder);
            } else {
                orderDao.addOrder(importOrder);
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
    public boolean exportOrder(int id, String fileName) {
        Order orderToExport = orderMapper.orderDtoToOrder(findOrderById(id));
        try {
            if (orderToExport != null) {
                return CsvUtil.exportCsvFile(toList(orderToExport), fileName);
            } else {
                log.error("Неверный № заказа");
                return false;
            }
        } catch (WrongFileFormatException e) {
            log.error("Неверный формат файла");
            return false;
        }
    }

    @Override
    public List<String> toList(Order order) {
        List<String> orderAsList = new ArrayList<>();
        orderAsList.add(String.valueOf(order.getId()));
        orderAsList.add(String.valueOf(order.getSubmissionDate()));
        orderAsList.add(String.valueOf(order.getStartDate()));
        orderAsList.add(String.valueOf(order.getEndDate()));
        orderAsList.add(order.getKindOfWork());
        orderAsList.add(String.valueOf(order.getCost()));
        orderAsList.add(order.getStatus().toString());
        orderAsList.add(String.valueOf(order.getGaragePlace().getGarageId()));
        orderAsList.add(String.valueOf(order.getGaragePlace().getId()));
        order.getMasters().stream()
                .forEach(master -> orderAsList.add(String.valueOf(master.getId())));
        return orderAsList;
    }

    @Override
    public void saveState() {
        SerializeUtil.saveState(getAllOrders(), "SerialsOrders.out");
    }

    @Override
    public void loadState() {
        orderDao.setAllOrders(SerializeUtil.loadState(Order.class, "SerialsOrders.out"));
    }

    private Comparator getOrderComparator(String sortBy) {
        Comparator orderComparator = null;
        if (sortBy != null) {
            switch (sortBy) {
                case "byCost":
                    orderComparator = OrderByCostComparator.getInstance();
                    break;
                case "byEndDate":
                    orderComparator = OrderByEndDateComparator.getInstance();
                    break;
                case "bySubmissionDate":
                    orderComparator = OrderBySubmissionDateComparator.getInstance();
                    break;
                case "byStartDate":
                    orderComparator = OrderByStartDateComparator.getInstance();
                    break;
            }
        }
        return orderComparator;
    }

}

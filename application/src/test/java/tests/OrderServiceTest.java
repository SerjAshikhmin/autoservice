package tests;

import com.senla.courses.autoservice.dao.interfaces.IGaragePlaceDao;
import com.senla.courses.autoservice.dao.interfaces.IMasterDao;
import com.senla.courses.autoservice.dao.interfaces.IOrderDao;
import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.dto.mappers.GaragePlaceMapper;
import com.senla.courses.autoservice.dto.mappers.MasterMapper;
import com.senla.courses.autoservice.dto.mappers.OrderMapper;
import com.senla.courses.autoservice.exceptions.orderexceptions.OrderNotFoundException;
import com.senla.courses.autoservice.model.domain.GaragePlace;
import com.senla.courses.autoservice.model.domain.Master;
import com.senla.courses.autoservice.model.domain.Order;
import com.senla.courses.autoservice.model.enums.OrderStatus;
import com.senla.courses.autoservice.service.interfaces.IGarageService;
import com.senla.courses.autoservice.service.interfaces.IMasterService;
import com.senla.courses.autoservice.service.interfaces.IOrderService;
import config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class OrderServiceTest {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IMasterService masterService;
    @Autowired
    private IGarageService garageService;
    @Autowired
    private TestData testData;
    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IMasterDao masterDao;
    @Autowired
    private IGaragePlaceDao garagePlaceDao;
    @Autowired
    private MasterMapper masterMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GaragePlaceMapper garagePlaceMapper;

    @BeforeAll
    public static void startOrderServiceTests() {
        log.info("Starting order service tests");
    }

    @Test
    public void validateNewOrderAdding() {
        log.info("Validating new order adding");
        when(orderDao.addOrder(any(Order.class))).thenReturn(1);
        when(masterDao.getAllMasters()).thenReturn(testData.getMastersList());
        when(garagePlaceDao.getGaragePlaceById(1, 1)).thenReturn(testData.getGarageList().get(0).getGaragePlaces().get(0));
        List<MasterDto> masters1 = new ArrayList<>();
        masters1.add(masterService.findMasterByName("Evgeniy"));
        List<MasterDto> masters2 = new ArrayList<>();

        orderService.addOrder(new OrderDto(1, LocalDateTime.of(2020, Month.JUNE, 1, 11, 0),
                LocalDateTime.of(2020, Month.JUNE, 1, 12, 0),
                LocalDateTime.of(2020, Month.JUNE, 1, 13, 0),
                "Oil change", 1000, garageService.findGaragePlaceById(1, 1), masters1, OrderStatus.ACCEPTED));
        orderService.addOrder(new OrderDto(1, LocalDateTime.of(2020, Month.JUNE, 1, 11, 0),
                LocalDateTime.of(2020, Month.JUNE, 1, 12, 0),
                LocalDateTime.of(2020, Month.JUNE, 1, 13, 0),
                "Oil change", 1000, garageService.findGaragePlaceById(1, 1), masters2, OrderStatus.ACCEPTED));

        verify(orderDao).addOrder(any(Order.class));
    }

    @Test
    public void validateRemovingOrder() {
        log.info("Validating order removing");
        when(orderDao.removeOrder(any(Order.class))).thenReturn(1);
        when(orderDao.getAllOrders()).thenReturn(testData.getOrdersList());

        orderService.removeOrder(1);

        verify(orderDao, atLeastOnce()).removeOrder(any(Order.class));
        verify(masterDao, atLeastOnce()).updateMaster(any(Master.class));
        verify(garagePlaceDao, atLeastOnce()).updateGaragePlace(any(GaragePlace.class));
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.removeOrder(5);
        });
    }

    @Test
    public void validateGettingAllOrders() {
        log.info("Validating getting all orders");
        when(orderDao.getAllOrders()).thenReturn(testData.getOrdersList());
        List<Order> expectedResult = testData.getOrdersList();

        List<OrderDto> result = orderService.getAllDtoOrders();

        assertEquals(result, orderMapper.orderListToOrderDtoList(expectedResult));
        verify(orderDao, atLeastOnce()).getAllOrders();
    }

    @Test
    public void validateGettingAllOrdersSorted() {
        log.info("Validating getting all orders sorted");
        List<Order> allOrdersSortedByCostExpected = new ArrayList<>(testData.getOrdersList());
        Collections.sort(allOrdersSortedByCostExpected, (order, t1) -> {
            if(order.getCost() > t1.getCost())
                return 1;
            else if(order.getCost()< t1.getCost())
                return -1;
            else
                return 0;
        });
        List<Order> allOrdersSortedByEndDateExpected = new ArrayList<>(testData.getOrdersList());
        Collections.sort(allOrdersSortedByEndDateExpected, Comparator.comparing(Order::getEndDate));
        List<Order> allOrdersSortedByStartDateExpected = new ArrayList<>(testData.getOrdersList());
        Collections.sort(allOrdersSortedByStartDateExpected, Comparator.comparing(Order::getStartDate));
        List<Order> allOrdersSortedBySubmissionDateExpected = new ArrayList<>(testData.getOrdersList());
        Collections.sort(allOrdersSortedBySubmissionDateExpected, Comparator.comparing(Order::getSubmissionDate));
        List<Order> noSortedExpected = testData.getOrdersList();

        List<OrderDto> allOrdersSortedByCost = orderService.getAllOrdersSorted("byCost");
        List<OrderDto> allOrdersSortedByEndDate = orderService.getAllOrdersSorted("byEndDate");
        List<OrderDto> allOrdersSortedByStartDate = orderService.getAllOrdersSorted("byStartDate");
        List<OrderDto> allOrdersSortedBySubmissionDate = orderService.getAllOrdersSorted("bySubmissionDate");
        List<OrderDto> noSorted = orderService.getAllOrdersSorted("wrongSortMethod");

        assertEquals(allOrdersSortedByCost, orderMapper.orderListToOrderDtoList(allOrdersSortedByCostExpected));
        assertEquals(allOrdersSortedByEndDate, orderMapper.orderListToOrderDtoList(allOrdersSortedByEndDateExpected));
        assertEquals(allOrdersSortedByStartDate, orderMapper.orderListToOrderDtoList(allOrdersSortedByStartDateExpected));
        assertEquals(allOrdersSortedBySubmissionDate, orderMapper.orderListToOrderDtoList(allOrdersSortedBySubmissionDateExpected));
        assertEquals(noSorted, orderMapper.orderListToOrderDtoList(noSortedExpected));
        verify(orderDao, atLeastOnce()).getAllOrders();
    }

    @Test
    public void validateGettingAllOrdersInProgress() {
        log.info("Validating getting all orders in progress");
        List<Order> expectedResult = testData.getAllOrdersInProgress();

        List<OrderDto> result = orderService.getAllOrdersInProgress(null);

        assertEquals(result, orderMapper.orderListToOrderDtoList(expectedResult));
        verify(orderDao, atLeastOnce()).getAllOrders();
    }

    @Test
    public void validateGettingNearestFreeDate() {
        log.info("Validating getting nearest free date");
        LocalDateTime expectedResult = testData.getNearestFreeDate();

        LocalDateTime result = orderService.getNearestFreeDate();

        assertEquals(result, expectedResult);
        verify(orderDao, atLeastOnce()).getAllOrders();
    }

    @Test
    public void validateGettingMastersByOrder() throws OrderNotFoundException {
        log.info("Validating getting masters by order");
        when(orderDao.getMastersByOrder(testData.getOrdersList().get(0))).thenReturn(testData.getOrdersList().get(0).getMasters());
        List<Master> expectedResult = testData.getOrdersList().get(0).getMasters();

        List<MasterDto> result = orderService.getMastersByOrder(1);

        assertEquals(result, masterMapper.masterListToMasterDtoList(expectedResult));
        verify(orderDao, atLeastOnce()).getAllOrders();
    }

    @Test
    public void validateGettingOrdersByPeriod() {
        log.info("Validating getting orders by period");
        Order expectedResult = testData.getOrdersList().get(0);

        OrderDto result = orderService.getOrdersByPeriod(LocalDateTime.of(2020, Month.JUNE, 1, 9, 0),
                        LocalDateTime.of(2020, Month.JUNE, 30, 18, 0), null).get(0);

        assertEquals(result, orderMapper.orderToOrderDto(expectedResult));
        verify(orderDao, atLeastOnce()).getAllOrders();
    }

    @Test
    public void validateFindingOrderById() {
        log.info("Validating finding order by id");
        Order expectedResult = testData.getOrdersList().get(0);

        OrderDto result = orderService.findOrderById(1);

        assertEquals(result, orderMapper.orderToOrderDto(expectedResult));
        verify(orderDao, atLeastOnce()).getAllOrders();
    }

    @Test
    public void validateFindingUnknownOrderById() {
        log.info("Validating finding unknown order by id");

        assertThrows(OrderNotFoundException.class, () -> {
            orderService.findOrderById(10);
        });
    }

    @Test
    public void validateCancellingOrder() {
        log.info("Validating order cancelling");
        when(orderDao.updateOrder(any(Order.class))).thenReturn(1);

        orderService.cancelOrder(1);

        verify(orderDao, atLeastOnce()).updateOrder(any(Order.class));
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.cancelOrder(5);
        });
    }

    @Test
    public void validateClosingOrder() {
        log.info("Validating order closing");

        orderService.closeOrder(1);

        verify(orderDao, atLeastOnce()).updateOrder(any(Order.class));
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.closeOrder(5);
        });
    }

    @Test
    public void validateOrderTimeUpdating() {
        log.info("Validating order time updating");
        Order order = testData.getOrdersList().get(0);

        orderService.updateOrderTime(orderMapper.orderToOrderDto(order), LocalDateTime.of(2020, Month.JUNE, 1, 12, 0),
                LocalDateTime.of(2020, Month.JUNE, 1, 15, 0));

        verify(orderDao, atLeastOnce()).updateOrder(any(Order.class));
    }

    @AfterAll
    public static void endMasterServiceTests() {
        log.info("Order service tests are completed");
    }

}

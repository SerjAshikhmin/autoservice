package tests;

import com.senla.courses.autoservice.model.Garage;
import com.senla.courses.autoservice.model.GaragePlace;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import com.senla.courses.autoservice.model.enums.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class TestData {

    private List<Master> mastersList;
    private List<Garage> garageList;
    private List<Order> ordersList;

    public TestData() {
        mastersList = new ArrayList<>();
        mastersList.add(new Master(1, "Evgeniy", 3));
        mastersList.add(new Master(2, "Alex", 2));
        mastersList.add(new Master(3, "Ivan", 5));

        garageList = new ArrayList<>();
        Garage firstGarage = new Garage(1, "Orel-Moskovskaya-22", new ArrayList<>());
        firstGarage.getGaragePlaces().add(new GaragePlace(1, firstGarage, "Car lift", 8));
        firstGarage.getGaragePlaces().add(new GaragePlace(2, firstGarage, "Pit", 12));
        firstGarage.getGaragePlaces().add(new GaragePlace(3, firstGarage, "Car lift", 8));
        firstGarage.getGaragePlaces().add(new GaragePlace(4, firstGarage, "Car lift", 8));
        garageList.add(firstGarage);
        Garage secGarage = new Garage(2, "Orel-Naugorskaya-20", new ArrayList<>());
        garageList.add(secGarage);

        ordersList = new ArrayList<>();
        ordersList.add(new Order(1, LocalDateTime.of(2020, Month.JUNE, 1, 11, 0),
                LocalDateTime.of(2020, Month.JUNE, 2, 12, 0),
                LocalDateTime.of(2020, Month.JUNE, 2, 13, 0),
                "Oil change", 1000, garageList.get(0).getGaragePlaces().get(0), new ArrayList<Master>(), OrderStatus.ACCEPTED));
        ordersList.get(0).getMasters().add(mastersList.get(0));

        ordersList.add(new Order(2, LocalDateTime.of(2020, Month.MAY, 31, 13, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 14, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 15, 0),
                "Tire fitting", 300, garageList.get(0).getGaragePlaces().get(1), new ArrayList<Master>(), OrderStatus.ACCEPTED));
        ordersList.get(1).getMasters().add(mastersList.get(1));

        ordersList.add(new Order(3, LocalDateTime.of(2020, Month.MAY, 31, 10, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 11, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 12, 0),
                "Diagnostics", 500, garageList.get(0).getGaragePlaces().get(2), new ArrayList<Master>(), OrderStatus.ACCEPTED));
        ordersList.get(2).getMasters().add(mastersList.get(2));

        Master master1 = mastersList.get(0);
        master1.setBusy(true);
        Master master3 = mastersList.get(2);
        master3.setBusy(true);
    }

    public List<Master> getAllFreeMasters() {
        List<Master> freeMasters = new ArrayList<>();
        getMastersList().forEach(master -> {if (!master.isBusy()) freeMasters.add(master);});
        return freeMasters;
    }

    public List<GaragePlace> getAllFreePlaces() {
        List<GaragePlace> freePlaces = new ArrayList<>();
        getGarageList().stream()
                .forEach(garage -> garage.getGaragePlaces().stream()
                        .filter(garagePlace -> !garagePlace.isBusy())
                        .forEach(garagePlace -> freePlaces.add(garagePlace)));
        return freePlaces;
    }

    public List<Order> getAllOrdersInProgress() {
        return getOrdersList().stream()
                .filter(order -> order.getStatus() == OrderStatus.IN_WORK)
                .collect(Collectors.toList());
    }

    public LocalDateTime getNearestFreeDate() {
        List<Order> allOrders = ordersList;
        final LocalDateTime nearestFreeDate = allOrders.get(0).getEndDate();
        return allOrders.stream()
                .filter(order -> order.getEndDate().compareTo(nearestFreeDate) == -1)
                .findFirst().get().getEndDate();
    }

}

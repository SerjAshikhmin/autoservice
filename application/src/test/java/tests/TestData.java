package tests;

import com.senla.courses.autoservice.dto.GarageDto;
import com.senla.courses.autoservice.dto.GaragePlaceDto;
import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.model.enums.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class TestData {

    private List<MasterDto> mastersList;
    private List<GarageDto> garageList;
    private List<OrderDto> ordersList;

    public TestData() {
        mastersList = new ArrayList<>();
        mastersList.add(new MasterDto(1, "Evgeniy", 3));
        mastersList.add(new MasterDto(2, "Alex", 2));
        mastersList.add(new MasterDto(3, "Ivan", 5));

        garageList = new ArrayList<>();
        GarageDto firstGarage = new GarageDto(1, "Orel-Moskovskaya-22", new ArrayList<>());
        firstGarage.getGaragePlaces().add(new GaragePlaceDto(1, firstGarage, "Car lift", 8));
        firstGarage.getGaragePlaces().add(new GaragePlaceDto(2, firstGarage, "Pit", 12));
        firstGarage.getGaragePlaces().add(new GaragePlaceDto(3, firstGarage, "Car lift", 8));
        firstGarage.getGaragePlaces().add(new GaragePlaceDto(4, firstGarage, "Car lift", 8));
        garageList.add(firstGarage);
        GarageDto secGarage = new GarageDto(2, "Orel-Naugorskaya-20", new ArrayList<>());
        garageList.add(secGarage);

        ordersList = new ArrayList<>();
        ordersList.add(new OrderDto(1, LocalDateTime.of(2020, Month.JUNE, 1, 11, 0),
                LocalDateTime.of(2020, Month.JUNE, 2, 12, 0),
                LocalDateTime.of(2020, Month.JUNE, 2, 13, 0),
                "Oil change", 1000, garageList.get(0).getGaragePlaces().get(0), new ArrayList<MasterDto>(), OrderStatus.ACCEPTED));
        ordersList.get(0).getMasters().add(mastersList.get(0));

        ordersList.add(new OrderDto(2, LocalDateTime.of(2020, Month.MAY, 31, 13, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 14, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 15, 0),
                "Tire fitting", 300, garageList.get(0).getGaragePlaces().get(1), new ArrayList<MasterDto>(), OrderStatus.ACCEPTED));
        ordersList.get(1).getMasters().add(mastersList.get(1));

        ordersList.add(new OrderDto(3, LocalDateTime.of(2020, Month.MAY, 31, 10, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 11, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 12, 0),
                "Diagnostics", 500, garageList.get(0).getGaragePlaces().get(2), new ArrayList<MasterDto>(), OrderStatus.ACCEPTED));
        ordersList.get(2).getMasters().add(mastersList.get(2));

        MasterDto master1 = mastersList.get(0);
        master1.setBusy(true);
        MasterDto master3 = mastersList.get(2);
        master3.setBusy(true);
    }

    public List<MasterDto> getAllFreeMasters() {
        List<MasterDto> freeMasters = new ArrayList<>();
        getMastersList().forEach(master -> {if (!master.isBusy()) freeMasters.add(master);});
        return freeMasters;
    }

    public List<GaragePlaceDto> getAllFreePlaces() {
        List<GaragePlaceDto> freePlaces = new ArrayList<>();
        getGarageList().stream()
                .forEach(garage -> garage.getGaragePlaces().stream()
                        .filter(garagePlace -> !garagePlace.isBusy())
                        .forEach(garagePlace -> freePlaces.add(garagePlace)));
        return freePlaces;
    }

    public List<OrderDto> getAllOrdersInProgress() {
        return getOrdersList().stream()
                .filter(order -> order.getStatus() == OrderStatus.IN_WORK)
                .collect(Collectors.toList());
    }

    public LocalDateTime getNearestFreeDate() {
        List<OrderDto> allOrders = ordersList;
        final LocalDateTime nearestFreeDate = allOrders.get(0).getEndDate();
        return allOrders.stream()
                .filter(order -> order.getEndDate().compareTo(nearestFreeDate) == -1)
                .findFirst().get().getEndDate();
    }

}

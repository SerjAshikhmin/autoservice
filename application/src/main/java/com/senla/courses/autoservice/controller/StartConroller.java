package com.senla.courses.autoservice.controller;

import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.model.Garage;
import com.senla.courses.autoservice.model.GaragePlace;
import com.senla.courses.autoservice.model.Master;
import com.senla.courses.autoservice.model.Order;
import com.senla.courses.autoservice.model.enums.OrderStatus;
import com.senla.courses.autoservice.service.interfaces.IGarageService;
import com.senla.courses.autoservice.service.interfaces.IMasterService;
import com.senla.courses.autoservice.service.interfaces.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


@Controller
public class StartConroller {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IMasterService masterService;
    @Autowired
    private IGarageService garageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView startPage() {
        //fillInTestData();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("page");
        return modelAndView;
    }

    /*private void fillInTestData() {
        masterService.addMaster(new MasterDto(1, "Evgeniy", 3));
        masterService.addMaster(new MasterDto(2, "Alex", 2));
        masterService.addMaster(new MasterDto(3, "Ivan", 5));

        garageService.addGarage(new Garage(1, "Orel-Moskovskaya-22", new ArrayList<>()));
        garageService.addGarage(new Garage(2, "Orel-Naugorskaya-20", new ArrayList<>()));

        garageService.addGaragePlace(new GaragePlace(1, garageService.findGarageById(1), "Car lift", 8));
        garageService.addGaragePlace(new GaragePlace(2, garageService.findGarageById(1), "Pit", 12));
        garageService.addGaragePlace(new GaragePlace(3, garageService.findGarageById(1), "Car lift", 8));
        garageService.addGaragePlace(new GaragePlace(4, garageService.findGarageById(1), "Car lift", 8));

        List<Master> masters = new ArrayList<>();
        masters.add(masterService.findMasterByName("Evgeniy"));
        orderService.addOrder(new Order(1, LocalDateTime.of(2020, Month.JUNE, 1, 11, 0),
                LocalDateTime.of(2020, Month.JUNE, 1, 12, 0),
                LocalDateTime.of(2020, Month.JUNE, 1, 13, 0),
                "Oil change", 1000, garageService.findGaragePlaceById(1, 1), masters, OrderStatus.ACCEPTED));
        masters = new ArrayList<>();
        masters.add(masterService.findMasterByName("Alex"));
        orderService.addOrder(new Order(2, LocalDateTime.of(2020, Month.MAY, 31, 13, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 14, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 15, 0),
                "Tire fitting", 300, garageService.findGaragePlaceById(1, 2), masters, OrderStatus.ACCEPTED));
        masters = new ArrayList<>();
        masters.add(masterService.findMasterByName("Ivan"));
        orderService.addOrder(new Order(3, LocalDateTime.of(2020, Month.MAY, 31, 10, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 11, 0),
                LocalDateTime.of(2020, Month.MAY, 31, 12, 0),
                "Diagnostics", 500, garageService.findGaragePlaceById(1, 3), masters, OrderStatus.ACCEPTED));
    }*/
}

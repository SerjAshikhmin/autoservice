package com.senla.courses.autoservice.dto.mappers;

import com.senla.courses.autoservice.dto.GarageDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.model.Garage;
import com.senla.courses.autoservice.model.Order;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel="spring")
public interface OrderMapper {
    @FullMapping
    OrderDto orderToOrderDto(Order order);
    Order orderDtoToOrder(OrderDto order);

    @IterableMapping(qualifiedBy = FullMapping.class)
    @FullMapping
    List<OrderDto> orderListToOrderDtoList(List<Order> list);

    @Mapping(target = "garagePlaces", ignore = true)
    GarageDto garageToGarageDto(Garage garage);
}

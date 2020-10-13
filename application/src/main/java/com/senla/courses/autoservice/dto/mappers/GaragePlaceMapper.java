package com.senla.courses.autoservice.dto.mappers;

import com.senla.courses.autoservice.dto.GarageDto;
import com.senla.courses.autoservice.dto.GaragePlaceDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.model.Garage;
import com.senla.courses.autoservice.model.GaragePlace;
import com.senla.courses.autoservice.model.Order;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel="spring")
public interface GaragePlaceMapper {
    @FullMapping
    GaragePlaceDto garagePlaceToGaragePlaceDto(GaragePlace garage);
    GaragePlace garagePlaceDtoToGaragePlace(GaragePlaceDto garage);

    @IterableMapping(qualifiedBy = FullMapping.class)
    @FullMapping
    List<GaragePlaceDto> garagePlaceListToGaragePlaceDtoList(List<GaragePlace> list);

    @Mapping(target = "masters", ignore = true)
    @Mapping(target = "garagePlace", ignore = true)
    OrderDto orderToOrderDto(Order order);

    @Mapping(target = "garagePlaces", ignore = true)
    GarageDto garageToGarageDto(Garage garage);
}

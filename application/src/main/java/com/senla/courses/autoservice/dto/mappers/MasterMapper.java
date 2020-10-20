package com.senla.courses.autoservice.dto.mappers;

import com.senla.courses.autoservice.dto.GarageDto;
import com.senla.courses.autoservice.dto.MasterDto;
import com.senla.courses.autoservice.dto.OrderDto;
import com.senla.courses.autoservice.model.domain.Garage;
import com.senla.courses.autoservice.model.domain.Master;
import com.senla.courses.autoservice.model.domain.Order;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface MasterMapper {
    @FullMapping
    MasterDto masterToMasterDto(Master master);
    Master masterDtoToMaster(MasterDto masterDto);

    @IterableMapping(qualifiedBy = FullMapping.class)
    @FullMapping
    List<MasterDto> masterListToMasterDtoList(List<Master> list);

    @Mapping(target = "garagePlaces", ignore = true)
    GarageDto garageToGarageDto(Garage garage);

    @Mapping(target = "masters", ignore = true)
    @Mapping(target = "garagePlace", ignore = true)
    OrderDto orderToOrderDto(Order order);
}

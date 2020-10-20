package com.senla.courses.autoservice.dto.mappers;

import com.senla.courses.autoservice.dto.GarageDto;
import com.senla.courses.autoservice.model.domain.Garage;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel="spring", uses = GaragePlaceMapper.class)
public interface GarageMapper {
    @FullMapping
    GarageDto garageToGarageDto(Garage garage);
    Garage garageDtoToGarage(GarageDto garage);

    @IterableMapping(qualifiedBy = FullMapping.class)
    @FullMapping
    List<GarageDto> garageListToGarageDtoList(List<Garage> list);
}

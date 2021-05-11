package com.senla.courses.autoservice.dto.validators;

import com.senla.courses.autoservice.dto.GaragePlaceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Service
public class GaragePlaceDtoValidator implements Validator {

    @Autowired
    private GarageDtoValidator garageDtoValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return GaragePlaceDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GaragePlaceDto garagePlace = (GaragePlaceDto) target;
        if (garagePlace.getId() <= 0) {
            errors.rejectValue("id", "id.negative", "GaragePlace's id must be more than 0");
        }
        if (garagePlace.getType().isBlank()) {
            errors.rejectValue("type", "type.empty", "GaragePlace's type must not be empty");
        }
        if (garagePlace.getType().length() > 30) {
            errors.rejectValue("type", "type.tooLong", "GaragePlace's name must not be more than 30 characters");
        }
        if (garagePlace.getArea() <= 0 || garagePlace.getArea() > 100) {
            errors.rejectValue("area", "area.outOfRange", "GaragePlace's area must be more than 0 and less than 100");
        }
        if (garagePlace.getGarage() != null) {
            garageDtoValidator.validate(garagePlace.getGarage(), errors);
        }
    }
}

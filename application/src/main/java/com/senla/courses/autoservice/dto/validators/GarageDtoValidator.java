package com.senla.courses.autoservice.dto.validators;

import com.senla.courses.autoservice.dto.GarageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Service
public class GarageDtoValidator implements Validator {

    @Autowired
    private GaragePlaceDtoValidator garagePlaceDtoValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return GarageDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GarageDto garage = (GarageDto) target;
        if (garage.getAddress().isBlank()) {
            errors.rejectValue("address", "address.empty", "Garage's address must not be empty");
        }
        if (garage.getAddress().length() > 100) {
            errors.rejectValue("address", "address.tooLong", "Garage's address must not be more than 100 characters");
        }
        if (garage.getId() <= 0) {
            errors.rejectValue("id", "id.negative", "Garage's id must be more than 0");
        }
        garage.getGaragePlaces().forEach(garagePlaceDto -> {
            garagePlaceDtoValidator.validate(garagePlaceDto, errors);
        });
    }
}

package com.senla.courses.autoservice.dto.validators;

import com.senla.courses.autoservice.dto.MasterDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Service
public class MasterDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MasterDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MasterDto master = (MasterDto) target;
        if (master.getName().isBlank()) {
            errors.rejectValue("name", "name.empty", "Master's name must not be empty");
        }
        if (master.getName().length() > 50) {
            errors.rejectValue("name", "name.tooLong", "Master's name must not be more than 50 characters");
        }
        if (master.getCategory() <= 0 || master.getCategory() > 20) {
            errors.rejectValue("category", "category.outOfRange", "Master's category must be more than 0 and less than 20");
        }
        if (master.getId() <= 0) {
            errors.rejectValue("id", "id.negative", "Master's id must be more than 0");
        }
    }
}

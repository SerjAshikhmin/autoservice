package com.senla.courses.autoservice.dto.validators;

import com.senla.courses.autoservice.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Service
public class OrderDtoValidator implements Validator {

    @Autowired
    private MasterDtoValidator masterDtoValidator;
    @Autowired
    private GaragePlaceDtoValidator garagePlaceDtoValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderDto order = (OrderDto) target;
        if (order.getId() <= 0) {
            errors.rejectValue("id", "id.negative", "Order's id must be more than 0");
        }
        if (order.getKindOfWork().isBlank()) {
            errors.rejectValue("kindOfWork", "kindOfWork.empty", "Order's name must not be empty");
        }
        if (order.getCost() <= 0) {
            errors.rejectValue("cost", "cost.negative", "Order's cost must be more than 0");
        }
        order.getMasters().forEach(masterDto -> {
            masterDtoValidator.validate(masterDto, errors);
        });
        if (order.getGaragePlace() != null) {
            garagePlaceDtoValidator.validate(order.getGaragePlace(), errors);
        }
    }
}

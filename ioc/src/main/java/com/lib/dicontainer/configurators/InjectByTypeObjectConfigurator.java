package com.lib.dicontainer.configurators;


import com.lib.dicontainer.Application;
import com.lib.dicontainer.ApplicationContext;
import com.lib.dicontainer.annotations.InjectByType;
import com.lib.dicontainer.interfaces.ObjectConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class InjectByTypeObjectConfigurator implements ObjectConfigurator {

    private static final Logger logger = LoggerFactory.getLogger(InjectByTypeObjectConfigurator.class);

    @Override
    public void configure(Object obj, ApplicationContext context) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                try {
                    field.setAccessible(true);
                    Object fieldValue = context.getObject(field.getType());
                    field.set(obj, fieldValue);
                } catch (Exception e) {
                    logger.error("Ошибка инициализации поля");
                }
            }
        }
    }
}

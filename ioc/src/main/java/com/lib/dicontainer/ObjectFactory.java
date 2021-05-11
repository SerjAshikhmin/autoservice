package com.lib.dicontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectFactory {

    private static final Logger logger = LoggerFactory.getLogger(ObjectFactory.class);

    private final ApplicationContext context;

    public ObjectFactory(ApplicationContext context) {
        this.context = context;

    }

    public <T> T createObject(Class<T> implClass) {
        try {
            T obj = implClass.getDeclaredConstructor().newInstance();
            return obj;
        } catch (Exception e) {
            logger.error("Ошибка создания объекта типа " + implClass);
            return null;
        }
    }
}

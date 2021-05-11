package com.lib.dicontainer;


import com.lib.dicontainer.annotations.Singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private ObjectFactory objectFactory;
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public <T> T getObject(Class<T> clazz) {
        if (cache.containsKey(clazz)) {
            return (T) cache.get(clazz);
        }

        Class<? extends T> implClass = clazz;
        if (implClass.isInterface()) {
            implClass = config.getImplClass(clazz);
        }

        T obj = objectFactory.createObject(implClass);
        if (clazz.isAnnotationPresent(Singleton.class)) {
            cache.put(clazz, obj);
        }

        return obj;
    }

    public Map<Class, Object> getCache() {
        return cache;
    }
}

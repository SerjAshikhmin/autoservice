package com.lib.dicontainer;

import org.reflections.Reflections;

import java.util.Set;

public class Config {

    private Reflections scanner;

    public Config(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }

    public <T> Class<? extends T> getImplClass(Class<T> clazz) {
        Set<Class<? extends T>> classes = scanner.getSubTypesOf(clazz);
        return classes.iterator().next();
    }

    public Reflections getScanner() {
        return scanner;
    }

}

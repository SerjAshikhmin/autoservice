package com.lib.dicontainer.interfaces;


import com.lib.dicontainer.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object obj, ApplicationContext context);
}

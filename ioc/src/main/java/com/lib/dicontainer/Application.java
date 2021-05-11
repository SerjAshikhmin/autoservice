package com.lib.dicontainer;

import com.lib.dicontainer.annotations.Singleton;
import com.lib.dicontainer.interfaces.ObjectConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class Application {

    private static List<ObjectConfigurator> configurators = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void run(String packageToScan) {
        Config config = new Config(packageToScan);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setObjectFactory(objectFactory);

        for (Class<? extends ObjectConfigurator> aClass: context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            try {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                logger.error("Ошибка загрузки конфигураторов");
            }
        }

        createSingletonsByAnnotation(config, context, Singleton.class);

        for (Object obj : context.getCache().values()) {
            configurators.forEach(objectConfigurator -> objectConfigurator.configure(obj, context));
        }
    }

    private static void createSingletonsByAnnotation(Config config, ApplicationContext context, Class<? extends Annotation> annotation) {
        for (Class<?> aClass : config.getScanner().getTypesAnnotatedWith(annotation)) {
            context.getObject(aClass);
        }
    }

}

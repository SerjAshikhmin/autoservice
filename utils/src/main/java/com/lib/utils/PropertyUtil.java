package com.lib.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil {
    private static Map<String, String> properties = new HashMap<>();

    public static void loadConfig(String propertiesPath) {
        try {
            File configFile = new File(propertiesPath);
            Properties config = new Properties();
            config.load(new FileReader(configFile));
            for (String propertyName : config.stringPropertyNames()) {
                properties.put(propertyName, config.getProperty(propertyName));
            }
        } catch (FileNotFoundException e) {
            ConsoleHelper.writeMessage("Файл настроек не найден");
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Ошибка ввода/вывода");
        }
    }

    public static String getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    public static Map<String, String> getProperties() {
        return properties;
    }
}

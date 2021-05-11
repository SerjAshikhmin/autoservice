package com.senla.courses.autoservice.utils;


import com.lib.utils.ConsoleHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtil {

    public static <T> void saveState(List<T> objects, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeInt(objects.size());
            for (T obj: objects) {
                out.writeObject(obj);
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Ошибка ввода/вывода");
        }
    }

    public static <T> List<T> loadState(Class<T> clazz, String fileName) {
        List<T> objects = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            T obj;
            int numberOfObjects = ois.readInt();
            for (int i = 0; i < numberOfObjects; i++) {
                obj = (T) ois.readObject();
                objects.add(obj);
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Ошибка ввода/вывода");
        } catch (ClassNotFoundException e) {
            ConsoleHelper.writeMessage("Ошибка десериализации");
        } finally {
            return objects;
        }
    }
}

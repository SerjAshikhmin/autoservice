package com.lib.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(Object message) {
        if (message != null) {
            if (message instanceof List) {
                for (Object object : (List) message) {
                    System.out.println(object);
                }
            } else {
                System.out.println(message);
            }
        }
    }

    public static String readString() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода");
            return readString();
        }
    }

}

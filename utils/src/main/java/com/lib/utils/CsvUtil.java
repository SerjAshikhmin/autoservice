package com.lib.utils;


import com.lib.utils.exceptions.WrongFileFormatException;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class CsvUtil {

    public static List<String> importCsvFile(String fileName) throws WrongFileFormatException {
        if (!fileName.endsWith(".csv")) {
            throw new WrongFileFormatException();
        }
        List<String> data;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder stringData = new StringBuilder();
            String attribute;
            while ((attribute = reader.readLine()) != null) {
                stringData.append(attribute);
            }
            data = Arrays.asList(stringData.toString().split(","));
        } catch (IOException e) {
            return null;
        }

        return data;
    }

    public static boolean exportCsvFile(List<String> data, String fileName) throws WrongFileFormatException {
        if (!fileName.endsWith(".csv")) {
            throw new WrongFileFormatException();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String stringData = "";
            for (String attrubute : data) {
                stringData += attrubute +",";
            }
            writer.write(stringData.substring(0, stringData.length() - 1));
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}

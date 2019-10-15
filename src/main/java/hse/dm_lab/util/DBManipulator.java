package hse.dm_lab.util;

import hse.dm_lab.model.Item;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DBManipulator {
    public static final String PATH = "src/main/resources/db/database.txt";

    public DBManipulator() {
        try {
            Path dbPath = Paths.get(PATH);
            Files.deleteIfExists(dbPath);
            Files.createFile(dbPath);
        } catch (IOException e) {
            System.out.println("Could not create database");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось создать базу данных");
            alert.showAndWait();
        }
    }

    public void deleteDB() {
        try {
            if (Files.exists(Paths.get(PATH))) {
                Files.delete(Paths.get(PATH));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setHeaderText("Успех");
                alert.setContentText("База данных успешно удалена");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка");
                alert.setContentText("Не существует базы для удаления");
                alert.showAndWait();
            }
        } catch (IOException e) {
            System.out.println("Could not delete database");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось удалить базу данных");
            alert.showAndWait();
        }
    }

    public List<Item> showAll() {
        try {
            List<Item> items = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("../database.txt"));
            String currentLine;
            while((currentLine = reader.readLine()) != null) {
                String[] fields = currentLine.split("|");
                Integer id = Integer.parseInt(fields[0]);
                String fio = fields[1];
                Boolean sex = Integer.parseInt(fields[2]) == 1;
                Integer claimCount = Integer.parseInt(fields[3]);
                Character role = fields[4].substring(0, 1).toCharArray()[0];
                items.add(new Item(id, fio, sex, claimCount, role));
            }
            return items;
        } catch (FileNotFoundException e) {
            System.out.println("File was not found. Probably you have not created one.");
        } catch (IOException e) {
            System.out.println("Exception while reading");
            System.out.println("Cause: " + e.getMessage());
        }
        return null;
    }
}

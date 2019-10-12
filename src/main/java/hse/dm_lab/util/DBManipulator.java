package hse.dm_lab.util;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

}

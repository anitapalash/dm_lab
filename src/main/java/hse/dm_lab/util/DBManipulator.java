package hse.dm_lab.util;

import hse.dm_lab.model.Item;
import hse.dm_lab.model.ItemDTO;
import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DBManipulator {
    private static final String PATH = "src/main/resources/db/database.txt";

    public void createDB() {
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
            BufferedReader reader = new BufferedReader(new FileReader(PATH));
            List<ItemDTO> items = new ArrayList<>();
            String currentLine;
            while((currentLine = reader.readLine()) != null) {
                String[] fields = currentLine.split("\\|");
                Integer id = Integer.parseInt(fields[0]);
                String fio = fields[1];
                Boolean sex = Integer.parseInt(fields[2]) == 1;
                Integer claimCount = Integer.parseInt(fields[3]);
                Character role = fields[4].substring(0, 1).toCharArray()[0];
                items.add(new ItemDTO(id, fio, sex, claimCount, role));
            }
            if (items.isEmpty()) {
                return new ArrayList<>();
            } else {
                List<Item> result = new ArrayList<>();
                for (ItemDTO item : items) {
                    result.add(ItemConverter.modelToEntity(item));
                }
                return result;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found. Probably you have not created one.");
            System.out.println("Cause: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception while reading");
            System.out.println("Cause: " + e.getMessage());
        }
        return null;
    }

    public void saveToDB(Item object) {
        try {
            BufferedWriter writer = new BufferedWriter(new PrintWriter(new FileOutputStream(PATH, true)));
            ItemDTO item = ItemConverter.entityToModel(object);
            if (getItem(item.getId()) != null) {
                throw new Exception("Элемент с таким идентификатором уже существует");
            }
            StringBuilder sb = new StringBuilder("");
            sb.append(item.getId());
            sb.append("|");
            sb.append(item.getFio());
            sb.append("|");
            sb.append(item.getSex() ? 1 : 0);
            sb.append("|");
            sb.append(item.getClaimCount());
            sb.append("|");
            sb.append(item.getRole());
            sb.append("\n");
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("Произошла ошибка при записи в базу данных");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Произошла ошибка при записи в базу данных");
            alert.showAndWait();
        }
    }

    public void saveToDB(List<Item> items) {
        for (Item i : items) {
            saveToDB(i);
        }
    }

    public Item getItem(Integer id) {
        List<Item> items = showAll();
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public Integer getNewId() {
        List<Integer> ids = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PATH));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                ids.add(Integer.parseInt(currentLine.substring(0, 1)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!ids.isEmpty()) {
            ids.sort(new IdComparator());
            return ids.get(ids.size() - 1) + 1;
        } else {
            return 1;
        }
    }

    static class IdComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }
}

package hse.dm_lab.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hse.dm_lab.model.Item;
import hse.dm_lab.model.ItemDTO;
import javafx.scene.control.Alert;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DBManipulator {
    private static final String PATH = "src/main/resources/db/database.txt";
    private final Gson gson = new Gson();

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
                if (!currentLine.trim().isEmpty()) {
                    String[] fields = currentLine.split("\\|");
                    Integer id = Integer.parseInt(fields[0]);
                    String fio = fields[1];
                    Boolean sex = Integer.parseInt(fields[2]) == 1;
                    Integer claimCount = Integer.parseInt(fields[3]);
                    Character role = fields[4].substring(0, 1).toCharArray()[0];
                    items.add(new ItemDTO(id, fio, sex, claimCount, role));
                }
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
            fileNotFoundException(e);
        } catch (IOException e) {
            readingException(e);
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
            writer.write(convertItemToString(item));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            writingException(e);
        }
    }

    private void saveToDB(List<Item> items) {
        try {
            BufferedWriter writer = new BufferedWriter(new PrintWriter(new FileOutputStream(PATH, false)));
            StringBuilder sb = new StringBuilder();
            for (Item object : items) {
                ItemDTO item = ItemConverter.entityToModel(object);
                sb.append(convertItemToString(item));
                sb.append("\n");
            }
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            writingException(e);
        }
    }

    private Item getItem(Integer id) {
        List<Item> items = showAll();
        for (Item item : items) {
            if (item.getId().equals(id)) {
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
                if (!currentLine.trim().isEmpty()) {
                    ids.add(Integer.parseInt(currentLine.substring(0, 1)));
                }
            }
        } catch (IOException e) {
            readingException(e);
        }
        if (!ids.isEmpty()) {
            ids.sort(new IdComparator());
            return ids.get(ids.size() - 1) + 1;
        } else {
            return 1;
        }
    }

    public void deleteItem(Integer itemId) {
        try {
            File file = new File(PATH);
            List<String> out = Files.lines(file.toPath())
                    .filter(line -> !line.contains(String.valueOf(itemId)))
                    .collect(Collectors.toList());
            Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Запись была успешно удалена");
        } catch (FileNotFoundException e) {
            fileNotFoundException(e);
        } catch (IOException e) {
            readingException(e);
        }
    }

    static class IdComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }

    private String convertItemToString(ItemDTO item) {
        StringBuilder sb = new StringBuilder();
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
        return sb.toString();
    }

    public void backup() {
        List<Item> preItems = showAll();
        List<ItemDTO> items = new ArrayList<>();
        for (Item item : preItems) {
            items.add(ItemConverter.entityToModel(item));
        }
        try {
            String savedItems = gson.toJson(items);
            Date currentDate = new Date();
            String fileName = "db-backup-" + currentDate.getTime();
            Path path = Paths.get(Paths.get(PATH).getParent().toString() + "/" + fileName);
            Files.createFile(path);
            BufferedWriter writer = new BufferedWriter(new PrintWriter(new FileOutputStream(path.toString(), true)));
            writer.write(savedItems);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not create backup file");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось создать копию базы данных");
            alert.showAndWait();
        }
    }

    public void recoverBackup(Path path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path.toString()));
            List<Item> result = new ArrayList<>();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                Type collectionType = new TypeToken<List<ItemDTO>>(){}.getType();
                List<ItemDTO> preItems = gson.fromJson(currentLine, collectionType);
                for (ItemDTO item : preItems) {
                    result.add(ItemConverter.modelToEntity(item));
                }
            }
            saveToDB(result);
        } catch (FileNotFoundException e) {
            fileNotFoundException(e);
        } catch (IOException e) {
            readingException(e);
        }
    }

    public List<Item> selectItems(Item filterItem) {
        List<Item> items = showAll();
        if (filterItem.getId() != null) {
            for (Item currentItem : items) {
                if (!currentItem.getId().equals(filterItem.getId())) {
                    items.remove(currentItem);
                }
            }
        }
        if (filterItem.getFio() != null) {
            for (Item currentItem : items) {
                if (!currentItem.getFio().equals(filterItem.getFio())) {
                    items.remove(currentItem);
                }
            }
        }
        if (filterItem.getClaimCount() != null) {
            for (Item currentItem : items) {
                if (!currentItem.getClaimCount().equals(filterItem.getClaimCount())) {
                    items.remove(currentItem);
                }
            }
        }
        if (filterItem.getSex() != null) {
            for (Item currentItem : items) {
                if (!currentItem.getSex().equals(filterItem.getSex())) {
                    items.remove(currentItem);
                }
            }
        }
        if (filterItem.getRole() != null) {
            for (Item currentItem : items) {
                if (!currentItem.getRole().equals(filterItem.getRole())) {
                    items.remove(currentItem);
                }
            }
        }
        return items;
    }

    private void fileNotFoundException(Exception e) {
        System.out.println("Файл не найден");
        System.out.println("Cause: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Файл не найден");
        alert.showAndWait();
    }

    private void readingException(Exception e) {
        System.out.println("Ошибка при чтении из файла");
        System.out.println("Cause: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Ошибка удаления записи");
        alert.showAndWait();
    }

    private void writingException(Exception e) {
        System.out.println("Произошла ошибка при записи в базу данных");
        System.out.println("Cause: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка");
        alert.setContentText("Произошла ошибка при записи в базу данных");
        alert.showAndWait();
    }
}

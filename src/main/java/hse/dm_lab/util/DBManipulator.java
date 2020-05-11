package hse.dm_lab.util;

import hse.dm_lab.model.Item;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBManipulator {
    private final String URl = "JDBC:mysql://localhost:3306/data_management?serverTimezone=UTC&characterEncoding=utf8&";
    private final String id = "root";
    private final String password = "alfresco";
    private Connection connection;

    public DBManipulator() {
        try {
            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            Class.forName(JDBC_DRIVER);
            Properties properties = new Properties();
            properties.setProperty("user", id);
            properties.setProperty("password", password);
            connection = DriverManager.getConnection(URl,properties);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Exception while connecting with MySQL");
            e.printStackTrace();
        }
    }

    public void createDB() {
        try {
            deleteDB();
            Statement statement = connection.createStatement();
            String createTableCmd = "CREATE TABLE data_management.claims (" +
                    "    id INT(64) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "    fio VARCHAR(128)," +
                    "    sex BOOL DEFAULT FALSE," +
                    "    claim_count INT(128)," +
                    "    role VARCHAR(32)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8";

            connection = DriverManager.getConnection(URl,id,password);
            statement.executeUpdate(createTableCmd);
            System.out.println("Table created");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText("Успех");
            alert.setContentText("Однотабличная база данных успешно создана");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("Could not create database");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось создать базу данных");
            alert.showAndWait();
        }
    }

    //удаление базы данных
    public void deleteDB() {
        try {
            Statement statement = connection.createStatement();
            String deleteCmd = "DROP TABLE IF EXISTS data_management.claims";
            statement.executeUpdate(deleteCmd);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText("Успех");
            alert.setContentText("База данных успешно удалена");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("Could not delete database");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось удалить базу данных");
            alert.showAndWait();
        }
    }

    //очистка базы данных
    public void clearDB() {
        try {
            Statement statement = connection.createStatement();
            String query = "TRUNCATE TABLE data_management.claims";
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText("Успех");
            alert.setContentText("База данных успешно очищена");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("Could not clear database");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось очистить базу данных");
            alert.showAndWait();
        }
    }

    public List<Item> showAll() {
        try {
            List<Item> result = new ArrayList<>();
            String query = "SELECT * FROM data_management.claims";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Item item = ItemConverter.entityFromResultSet(rs);
                result.add(item);
            }
            return result;
        } catch (SQLException e) {
            System.out.println("Ошибка во время выполнения select'a");
            e.printStackTrace();
        }
        return null;
    }

    public void saveToDB(Item object) {
        try {
            String query = "INSERT INTO data_management.claims (fio, sex, claim_count, role) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, object.getFio());
            statement.setBoolean(2, object.getSex().equals("Мужской"));
            statement.setInt(3, object.getClaimCount());
            statement.setString(4, object.getRole());

            statement.execute();
        } catch (Exception e) {
            writingException(e);
        }
    }

    public void deleteItem(Integer itemId) {
        try {
            Statement statement = connection.createStatement();
            String query = "DELETE FROM data_management.claims WHERE id = " + itemId;
            statement.executeUpdate(query);
            System.out.println("Запись была успешно удалена");
        } catch (SQLException e) {
            System.out.println("Could not clear database");
            System.out.println("Cause: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не удалось удалить элемент из базы данных");
            alert.showAndWait();
        }
    }

    public void selectItems(Item filterItem) {

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
